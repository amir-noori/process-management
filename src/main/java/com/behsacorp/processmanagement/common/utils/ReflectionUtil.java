package com.behsacorp.processmanagement.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

    public static ArrayList<Class> primitiveTypes;
    public static ArrayList<Class> primitiveNumberTypes;

    static {
        primitiveTypes = new ArrayList<>();
        primitiveTypes.add(Integer.class);
        primitiveTypes.add(int.class);
        primitiveTypes.add(Double.class);
        primitiveTypes.add(double.class);
        primitiveTypes.add(Long.class);
        primitiveTypes.add(long.class);
        primitiveTypes.add(String.class);
        primitiveTypes.add(Boolean.class);
        primitiveTypes.add(boolean.class);

        primitiveNumberTypes = new ArrayList<>();
        primitiveNumberTypes.add(Integer.class);
        primitiveNumberTypes.add(int.class);
        primitiveNumberTypes.add(Double.class);
        primitiveNumberTypes.add(double.class);
        primitiveNumberTypes.add(Long.class);
        primitiveNumberTypes.add(long.class);
    }

    public static boolean isPrimitiveNumberType(Class cls) {
        return primitiveNumberTypes.contains(cls);
    }

    public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            List<Class> foundClasses = findClasses(directory, packageName);
            classes.addAll(foundClasses);
        }
        Class[] allClasses = classes.toArray(new Class[classes.size()]);
        return allClasses;
    }

    public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists() && !directory.getPath().contains("!")) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static List<Field> getAnnotatedFields(Object requestedObj, Class annotation) {
        return getAnnotatedFieldsByClass(requestedObj.getClass(), requestedObj, annotation);
    }

    private static List<Field> getAnnotatedFieldsByClass(Class requestedClass, Object requestedObject, Class annotation) {

        Field[] fields = requestedClass.getDeclaredFields();
        ArrayList<Field> fieldList = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotation)) {
                fieldList.add(field);
            }
        }

        Class requestedSuperClass = requestedClass.getSuperclass();
        if (fieldList.isEmpty() && requestedSuperClass != Object.class) {
            fieldList = (ArrayList<Field>) getAnnotatedFieldsByClass(requestedSuperClass, requestedObject, annotation);
        }
        return fieldList;

    }

    public static Object callGetter(Object invokeMethod, Class requestedClass, Field field) {
        String getMethodName = "get" + StringUtils.capitalize(field.getName());
        Object value = null;
        try {
            Method getterMethod = requestedClass.getDeclaredMethod(getMethodName);
            value = getterMethod.invoke(invokeMethod);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void callSetter(Object object, Field field, Object value) {
        String setterMethodName = "set" + StringUtils.capitalize(field.getName());
        try {
            if (primitiveTypes.contains(field.getType())) {
                Class fieldType = field.getType();
                if (value == null) {
                    if (fieldType == String.class) {
                        value = "";
                    } else {
                        if (isPrimitiveNumberType(fieldType)) {
                            if (fieldType == Integer.class || fieldType == int.class) {
                                value = Integer.valueOf(0);
                            }
                            if (fieldType == Double.class || fieldType == double.class) {
                                value = Double.valueOf(123321);
                            }
                            if (fieldType == Long.class || fieldType == long.class) {
                                value = Long.valueOf(123321);
                            }
                        }
                    }
                } else {
                    Class valueClass = value.getClass();
                    if (valueClass == Integer.class || valueClass == int.class) {
                        if (fieldType == String.class) {
                            value = ((Integer) value).toString();
                        }
                    }
                    if (valueClass == Double.class || valueClass == double.class) {
                        if (fieldType == String.class) {
                            value = ((Double) value).toString();
                        }
                    }
                    if (valueClass == Long.class || valueClass == long.class) {
                        if (fieldType == String.class) {
                            value = ((Long) value).toString();
                        }
                    }
                    if (valueClass == String.class) {
                        if (fieldType == Integer.class || fieldType == int.class) {
                            value = Integer.parseInt((String) value);
                        }
                        if (fieldType == Double.class || fieldType == double.class) {
                            value = Double.parseDouble((String) value);
                        }
                        if (fieldType == Long.class || fieldType == long.class) {
                            value = Long.parseLong((String) value);
                        }
                    }
                }
            }
            try {
                Method setterMethod = object.getClass().getMethod(setterMethodName);
                setterMethod.invoke(object, value);
            } catch (NoSuchMethodException e) {
                for (Method method : object.getClass().getMethods()) {
                    if (method.getName().equals(setterMethodName)) {
                        method.invoke(object, value);
                    }
                }
            }

        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasType(Class target, Class type) {
        for (Class interfaceClass : target.getInterfaces()) {
            if (type.equals(interfaceClass)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isList(Class target) {
        return target.equals(List.class) || hasType(target.getClass(), List.class);
    }

    public static boolean isMap(Class target) {

        return target.equals(Map.class) || hasType(target.getClass(), Map.class);
    }

    public static Object createObjectFromClass(Class className) {
        Constructor defaultConstructor = null;
        for (Constructor constructor : className.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                defaultConstructor = constructor;
                break;
            }
        }
        Object object = null;
        try {
            assert defaultConstructor != null;
            object = defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return object;
    }

}
