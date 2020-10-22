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

public class SideOutput2 {
   private final static StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    public static void main(String[] args) throws Exception {




        List<Info> data = new ArrayList<>();
        data.add(new Info(0, 1, 0));
        data.add(new Info(0, 1, 2));
        data.add(new Info(0, 1, 4));
        data.add(new Info(1, 1, 0));
        data.add(new Info(1, 3, 0));
        data.add(new Info(1, 2, 0));
        data.add(new Info(2, 2, 0));
        DataStreamSource<Info> source = env.fromCollection(data);

        OutputTag<Info> zero = new OutputTag<Info>("zero.a") {
        };
        OutputTag<Info> one = new OutputTag<Info>("one.b") {
        };

        SingleOutputStreamOperator<Info> processStream = source.process(new ProcessFunction<Info, Info>() {
            @Override
            public void processElement(Info elements, Context context, Collector<Info> collector) throws Exception {


                if (elements.getA() == 0) {
                    context.output(zero, elements);
                } else if(elements.getA()==1){
                    context.output(one, elements);
                }
            }
        });

        DataStream<Info> zeroOutput = processStream.getSideOutput(zero);

        DataStream<Info> oneOutput = processStream.getSideOutput(one);

        zeroOutput.print();
        oneOutput.printToErr();

        env.execute("side output");
    }
}
