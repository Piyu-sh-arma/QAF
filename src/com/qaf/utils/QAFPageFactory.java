package com.qaf.utils;

import com.qaf.annotations.Find;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import org.openqa.selenium.WebDriver;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class QAFPageFactory {
    public static <T> void initElements(WebDriver parent, T page) {
        Field[] allFields = page.getClass().getDeclaredFields();
        for (Field field : allFields)
            if (field.isAnnotationPresent(Find.class)) {
//                System.out.println(field.getType().getName());
                Find find = field.getAnnotation(Find.class);
                With with = find.with();
                String value = find.value();
                try {
                    Constructor fieldClassConstructor = field.getType().getConstructor(WebDriver.class, With.class, String.class);
                    field.set(page, fieldClassConstructor.newInstance(parent, with, value));
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }

            }

    }

    public static <T extends QAFElement> void initElements(T parent) {
        Class componentClass = parent.getClass();
        Field[] allFields = componentClass.getDeclaredFields();
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Find.class)) {
                Find find = field.getAnnotation(Find.class);
                With with = find.with();
                String value = find.value();
                try {
                    Constructor fieldClassConstructor = field.getType().getConstructor(field.getType(), With.class, String.class);
                    field.set(parent, fieldClassConstructor.newInstance(parent, with, value));
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
