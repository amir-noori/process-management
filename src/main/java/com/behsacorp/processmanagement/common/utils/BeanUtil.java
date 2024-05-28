package com.behsacorp.processmanagement.common.utils;

import com.behsacorp.processmanagement.pm.dto.ActivityInstanceDto;
import com.behsacorp.processmanagement.pm.exception.PMException;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.camunda.bpm.engine.history.HistoricActivityInstance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanUtil {





    public static <T> List<T> copyProperties(List<? extends Object> fromList, Class<T> clazz) throws PMException {
        List<T> toList = new ArrayList<>();
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            for (Object from : fromList) {
                Object obj = ReflectionUtil.createObjectFromClass(clazz);
                copy(from, obj);
                toList.add((T) obj);
            }
        } catch (IllegalAccessException e) {
            throw new PMException("cannot copy properties!", e);
        }
        return toList;
    }

    public static void copy(Object from, Object to) throws IllegalAccessException {
        copy(from, to, Object.class);
    }

    public static void copy(Object from, Object to, Class depth) throws IllegalAccessException {
        Class fromClass = from.getClass();
        Class toClass = to.getClass();
        List<Field> fromFields = collectFields(fromClass, depth);
        List<Field> toFields = collectFields(toClass, depth);
        Field target;
        for (Field source : fromFields) {
            if ((target = findAndRemove(source, toFields)) != null) {
                target.set(to, source.get(from));
            }
        }
    }

    private static List<Field> collectFields(Class c, Class depth) {
        List<Field> accessibleFields = new ArrayList<>();
        do {
            int modifiers;
            for (Field field : c.getDeclaredFields()) {
                modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
                    accessibleFields.add(field);
                }
            }
            c = c.getSuperclass();
        } while (c != null && c != depth);
        return accessibleFields;
    }

    private static Field findAndRemove(Field field, List<Field> fields) {
        Field actual;
        for (Iterator<Field> i = fields.iterator(); i.hasNext();) {
            actual = i.next();
            if (field.getName().equals(actual.getName())
                    && field.getType().equals(actual.getType())) {
                i.remove();
                return actual;
            }
        }
        return null;
    }


}
