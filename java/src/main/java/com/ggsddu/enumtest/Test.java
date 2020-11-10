package com.ggsddu.enumtest;

public class Test {
    public static void main(String[] args) {
       Action action =  Action.valueOf("CREATE");
       action.setValue("aa");
        System.out.println(action.getValue());
        System.out.println(Action.valueOf("CREATE"));
    }
}
