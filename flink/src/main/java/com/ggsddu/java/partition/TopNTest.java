package com.ggsddu.java.partition;

import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;

/**
 * @author zhoup
 */
public class TopNTest {
    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        ArrayList<Tuple2<Integer, String>> data = new ArrayList<Tuple2<Integer, String>>();

        data.add(new Tuple2<Integer, String>(2, "zs"));
        data.add(new Tuple2<Integer, String>(4, "ls"));
        data.add(new Tuple2<Integer, String>(3, "ww"));
        data.add(new Tuple2<Integer, String>(1, "xw"));
        data.add(new Tuple2<Integer, String>(1, "aw"));
        data.add(new Tuple2<Integer, String>(1, "ew"));

        // 按数据插入的顺序获取前三条数据
        // env.fromCollection(data).first(3).print();

        // 根据数据中的第一列进行分组，获取每组的前2个元素
        // env.fromCollection(data).groupBy(0).first(2).print();

        // 根据数据中的第一列进行分组，并根据第二列进行组内排序，获取每组的前2个元素

        // env.fromCollection(data).groupBy(0).sortGroup(1, Order.ASCENDING).first(2).print();

        // 排序获取集合中的前三个元素
        // sortPartition在本地对所有数据进行全局排序
        env.fromCollection(data).sortPartition(0, Order.ASCENDING)
                .sortPartition(1, Order.DESCENDING)
                .first(3)
                .print();
    }
}
