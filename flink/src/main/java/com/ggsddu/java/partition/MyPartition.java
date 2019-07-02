package com.ggsddu.java.partition;

import org.apache.flink.api.common.functions.Partitioner;

/**
 * @author zhoup
 */
public class MyPartition implements Partitioner<Integer> {
    public int partition(Integer aLong, int i) {
        System.out.println("分区总数：" + i);
        if (aLong % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
