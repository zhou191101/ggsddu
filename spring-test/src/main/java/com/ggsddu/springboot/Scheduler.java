package com.ggsddu.springboot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(cron = "0 46 10 * * ?")
    public void test(){
        System.out.println("---------");
    }
}
