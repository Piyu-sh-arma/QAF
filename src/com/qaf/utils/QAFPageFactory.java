package com.qaf.utils;

import com.qaf.annotations.Find;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import jdk.nashorn.internal.objects.NativeDebug;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class QAFPageFactory {
    public static <T> void initElements(WebDriver parent, T pageObject) {
        Field[] allFields = pageObject.getClass().getDeclaredFields();
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Find.class)) {
                System.out.println(field.getType().getName());
                Find find = field.getAnnotation(Find.class);
                With with = find.with();
                String value = find.value();
                try {
                    Constructor fieldClassConstructor = field.getType().getConstructor(WebDriver.class, With.class, String.class);
                    field.set(pageObject, fieldClassConstructor.newInstance(parent, with, value));
                } catch (IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static <T1> void initElements(T1 componentObject) {
        Class componentClass = componentObject.getClass();
        Field[] allFields = componentClass.getDeclaredFields();
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Find.class)) {
                Find find = field.getAnnotation(Find.class);
                With with = find.with();
                String value = find.value();
                try {
                    Constructor fieldClassConstructor = field.getType().getConstructor(field.getType(), With.class, String.class);
                    field.set(componentObject, fieldClassConstructor.newInstance(componentObject, with, value));
                } catch (IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
