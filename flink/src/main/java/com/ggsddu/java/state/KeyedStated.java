package com.ggsddu.java.state;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Objects;


public class KeyedStated {
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(Tuple2.of("20138439", "ancdafa"), Tuple2.of("20138439", "ancfafa"),
                Tuple2.of("20138439", "ancdsfa"), Tuple2.of("20138439", "anceafa"))
                .keyBy(0)
                .map(new ListStateRichMapFunction())
                .print();
        env.execute("keyed state");
    }


    private static class ValueStateRichMapFunction extends RichMapFunction<Tuple2<String, String>, Tuple2<String, String>> {

        private transient ValueState<Long> sum;

        @Override
        public void open(Configuration parameters) throws Exception {
            ValueStateDescriptor<Long> descriptor = new ValueStateDescriptor<>("sum", Long.class);
            sum = getRuntimeContext().getState(descriptor);
        }

        @Override
        public Tuple2<String, String> map(Tuple2<String, String> value) throws Exception {
            Long currentSum = sum.value();
            if (Objects.isNull(currentSum)) {
                currentSum = 0L;
            }
            sum.update(++currentSum);
            return value;
        }
    }

    private static class ListStateRichMapFunction extends RichMapFunction<Tuple2<String, String>, Tuple2<String, String>> {

        private transient ListState<Long> eventTimes;

        @Override
        public void open(Configuration parameters) throws Exception {
            ListStateDescriptor<Long> descriptor = new ListStateDescriptor<>("eventTimes", Long.class);
            eventTimes = getRuntimeContext().getListState(descriptor);
        }

        @Override
        public Tuple2<String, String> map(Tuple2<String, String> value) throws Exception {
            eventTimes.add(Long.parseLong(value.f0));
            return value;
        }
    }
}
