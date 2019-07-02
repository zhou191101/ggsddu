package com.ggsddu.java.partition;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.ArrayList;

/**
 * @author zhoup
 */
public class LeftJoinTest {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        ArrayList<Tuple2<Integer, String>> list1 = new ArrayList<Tuple2<Integer, String>>();
        list1.add(new Tuple2<Integer, String>(1, "zhangsan"));
        list1.add(new Tuple2<Integer, String>(2, "lisi"));
        list1.add(new Tuple2<Integer, String>(3, "wangwu"));

        ArrayList<Tuple2<Integer, String>> list2 = new ArrayList<Tuple2<Integer, String>>();
        list2.add(new Tuple2<Integer, String>(1, "beijing"));
        list2.add(new Tuple2<Integer, String>(2, "shanghai"));
        list1.add(new Tuple2<Integer, String>(4, "chengdu"));


        env.fromCollection(list1).leftOuterJoin(env.fromCollection(list2))
                .where(0)
                .equalTo(0)
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple2<Integer, String>, Object>() {
                    public Object join(Tuple2<Integer, String> integerStringTuple2, Tuple2<Integer, String> integerStringTuple22) throws Exception {
                        if (integerStringTuple22 == null) {
                            return new Tuple3<Integer, String, String>(integerStringTuple2.f0, integerStringTuple2.f1, "null");
                        } else {
                            return new Tuple3<Integer, String, String>(integerStringTuple2.f0, integerStringTuple2.f1, integerStringTuple22.f1);
                        }
                    }
                }).print();
    }
}
