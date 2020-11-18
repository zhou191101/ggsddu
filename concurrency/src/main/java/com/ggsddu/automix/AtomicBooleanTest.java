package com.ggsddu.automix;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest {
    public static void main(String[] args) {


        AtomicBoolean flag = new AtomicBoolean(false);


        System.out.println(flag.compareAndSet(false, true));
    }

}
