package com.ggsddu.lock;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class TestSemaphore {

    public static void main(String[] args) throws InterruptedException {


        Semaphore semaphore = new Semaphore(1);
        new Timer("time").schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();

                    System.out.println("aaaaa");

                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }, 2000, 2000);

        while (true){
            Thread.sleep(3000);

            semaphore.acquire();
            System.out.println("sadad");
            semaphore.release();
        }
    }
}


