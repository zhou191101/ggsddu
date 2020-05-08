package com.ggsddu.java.state;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.*;
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
                .map(new MapStateRichMapFunction())
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


    private static class ReducingStateRichMapFunction extends RichMapFunction<Tuple2<String, String>, Tuple2<String, String>> {

        private transient ReducingState<Long> count;

        @Override
        public void open(Configuration parameters) throws Exception {
            ReducingStateDescriptor<Long> descriptor = new ReducingStateDescriptor<>("count", new ReduceFunction<Long>() {
                @Override
                public Long reduce(Long value1, Long value2) throws Exception {
                    return value1 + value2;
                }
            }, Long.class);
            count = getRuntimeContext().getReducingState(descriptor);
        }

        @Override
        public Tuple2<String, String> map(Tuple2<String, String> value) throws Exception {
            Long currentCount = count.get();
            if (Objects.isNull(currentCount)) {
                currentCount = 0L;
            }
            count.add(++currentCount);
            return value;
        }
    }

    private static class AggregatingStateRichMapFunction extends RichMapFunction<Tuple2<String, String>, Tuple2<String, String>> {

        private transient AggregatingState<Long, Long> counter;

        @Override
        public void open(Configuration parameters) throws Exception {
            AggregatingStateDescriptor<Long, Long, Long> descriptor =
                    new AggregatingStateDescriptor<>("counter", new AggregateFunction<Long, Long, Long>() {
                        @Override
                        public Long createAccumulator() {
                            return 0L;
                        }

                        @Override
                        public Long add(Long value, Long accumulator) {
                            return value;
                        }

                        @Override
                        public Long getResult(Long accumulator) {
                            return accumulator;
                        }

                        @Override
                        public Long merge(Long a, Long b) {
                            return a + b;
                        }
                    }, Long.class);
            counter = getRuntimeContext().getAggregatingState(descriptor);
        }

        @Override
        public Tuple2<String, String> map(Tuple2<String, String> value) throws Exception {
            Long currentCounter = counter.get();
            if (Objects.isNull(currentCounter)) {
                currentCounter = 0L;
            }
            counter.add(++currentCounter);
            return value;
        }
    }

    private static class MapStateRichMapFunction extends RichMapFunction<Tuple2<String, String>, Tuple2<String, String>> {

        private transient MapState<Long, Long> counter;

        @Override
        public void open(Configuration parameters) throws Exception {
            MapStateDescriptor<Long, Long> descriptor = new MapStateDescriptor<>("counter", Long.class, Long.class);
            counter = getRuntimeContext().getMapState(descriptor);
        }

        @Override
        public Tuple2<String, String> map(Tuple2<String, String> value) throws Exception {
            if (counter.contains(Long.parseLong(value.f0))) {
                Long count = counter.get(Long.parseLong(value.f0));
                counter.put(Long.parseLong(value.f0), ++count);
            } else {
                counter.put(Long.parseLong(value.f0), 1L);
            }
            return value;
        }
    }
}
