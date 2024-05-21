package com.behsacorp.processmanagement.common.utils;

public class SysUtil {

    public static String getenv(String name) {
        String value = System.getenv(name);
        if (StrUtil.isEmpty(value)) {
            return "";
        } else {
            return value;
        }
    }

}
