package com.ggsddu.utils;

public class SplitUtils {
    public static void main(String[] args) {

        String a = "test_";
        String b ="test_hahaha";

        if(b.contains(a)){
            System.out.println( b.split(a)[1]);
        }

    }
}
