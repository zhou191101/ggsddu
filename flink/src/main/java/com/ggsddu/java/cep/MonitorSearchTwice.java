package com.ggsddu.java.cep;


import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;
import java.util.Map;

public class MonitorSearchTwice {

    public static void main(String[] args) throws Exception {


        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        final DataStreamSource<Tuple3<String, String, Long>> source = env.fromElements(
                Tuple3.of("Tom", "帽子", 1L),
                Tuple3.of("Jerry", "衣服", 2L),
                Tuple3.of("Tom", "帽子", 3L),
                Tuple3.of("Jerry", "帽子", 1L),
                Tuple3.of("Tom", "衣服", 1L));

        final Pattern<Tuple3<String, String, Long>, Tuple3<String, String, Long>> pattern = Pattern.<Tuple3<String, String, Long>>begin("start")
                .where(new SimpleCondition<Tuple3<String, String, Long>>() {
                    @Override
                    public boolean filter(Tuple3<String, String, Long> items) throws Exception {
                        return "帽子".equals(items.f1);
                    }
                })
                .next("middle")
                .where(new SimpleCondition<Tuple3<String, String, Long>>() {
                    @Override
                    public boolean filter(Tuple3<String, String, Long> items) throws Exception {
                        return "帽子".equals(items.f1);
                    }
                });

        final KeyedStream<Tuple3<String, String, Long>, Tuple> keyedStream = source.keyBy(0);

        final PatternStream<Tuple3<String, String, Long>> result = CEP.pattern(keyedStream, pattern);

        result.select(new PatternSelectFunction<Tuple3<String, String, Long>, String>() {
            @Override
            public String select(Map<String, List<Tuple3<String, String, Long>>> map) throws Exception {
                final List<Tuple3<String, String, Long>> middle = map.get("middle");

                return middle.get(0).f0 + ": " + middle.get(0).f2 + ": " + "连续搜索两次帽子";
            }
        }).printToErr();

        env.execute("cep");
    }
}
