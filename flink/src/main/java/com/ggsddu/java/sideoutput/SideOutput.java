package com.ggsddu.java.sideoutput;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;


import java.util.ArrayList;
import java.util.List;

public class SideOutput {

    public static void main(String[] args) throws Exception {


        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        List<Tuple3<Integer, Integer, Integer>> data = new ArrayList<>();
        data.add(new Tuple3<>(0, 1, 0));
        data.add(new Tuple3<>(0, 1, 2));
        data.add(new Tuple3<>(0, 1, 4));
        data.add(new Tuple3<>(1, 1, 0));
        data.add(new Tuple3<>(1, 3, 0));
        data.add(new Tuple3<>(1, 2, 0));

        DataStreamSource<Tuple3<Integer, Integer, Integer>> source = env.fromCollection(data);

        OutputTag<Tuple3<Integer, Integer, Integer>> zero = new OutputTag<Tuple3<Integer, Integer, Integer>>("zero"){};
        OutputTag<Tuple3<Integer, Integer, Integer>> one = new OutputTag<Tuple3<Integer, Integer, Integer>>("one"){};

        SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> processStream = source.process(new ProcessFunction<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>() {
            @Override
            public void processElement(Tuple3<Integer, Integer, Integer> elements, Context context, Collector<Tuple3<Integer, Integer, Integer>> collector) throws Exception {


                if (elements.f0 == 0) {
                    context.output(zero, elements);
                } else {
                    context.output(one, elements);
                }
            }
        });

        DataStream<Tuple3<Integer, Integer, Integer>> zeroOutput = processStream.getSideOutput(zero);

        DataStream<Tuple3<Integer, Integer, Integer>> oneOutput = processStream.getSideOutput(one);

        zeroOutput.print();
        oneOutput.printToErr();

        env.execute("side output");
    }
}
