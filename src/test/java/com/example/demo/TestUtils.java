package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object objectToInject) {

        boolean wasPrivate = false;

        try {
            Field f = target.getClass().getDeclaredField(fieldName);

            if (!f.isAccessible()) {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, objectToInject);
            if (!wasPrivate == true) {
                f.setAccessible(false);
            }

        } catch (Exception ex) {
            System.out.println("Error :: Error while injecting object using reflection.");
            ex.printStackTrace();
        }
    }
}
