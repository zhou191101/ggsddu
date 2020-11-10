package com.ggsddu.json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {


        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        list1.add("C");


        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("B");
        list2.add("D");

        for (String a : list1) {
            for (String b : list2) {
                if (b.equals(a)) {
                    break;
                }
                System.out.println("aaa" + a);
                System.out.println("bbb" + b);
            }
            System.out.println("aaa111" + a);
        }
    }
}
