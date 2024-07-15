package com.ajava8.space.cutomAnnotation.paramLevel;

import java.lang.reflect.Field;

public class AnnotationProcessor {
    public static void processAnnotations(Object obj) {
        Class<?> clazz = obj.getClass();
        
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ToUpperCase.class)) {
                field.setAccessible(true);  // Make the field accessible if it is private
                try {
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        String upperCaseValue = ((String) value).toUpperCase();
                        field.set(obj, upperCaseValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
