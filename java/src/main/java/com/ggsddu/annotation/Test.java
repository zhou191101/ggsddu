package com.ggsddu.annotation;

import org.reflections.Reflections;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

        Reflections reflections=new Reflections("com.ggsddu.annotation.*");

      //  reflections.getConstructorsAnnotatedWith(A.class).forEach(constructor -> System.out.println(constructor.getAnnotatedReceiverType()));
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(A.class);
       // annotated.stream().filter(c->c.getName())
        annotated.stream().forEach(c-> System.out.println(c.getAnnotations()[0].toString().contains("aa")));
        System.out.println(annotated);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        System.out.println(ft.format(new Date()));
    }
}
