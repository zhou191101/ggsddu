package com.ggsddu.java.partition;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.configuration.Configuration;


/**
 * 全局累加器
 *
 * @author zhoup
 */
public class CounterTest {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<String> data = env.fromElements("a", "b", "c", "d");

        data.map(new RichMapFunction<String, String>() {

            private IntCounter counter = new IntCounter();

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                getRuntimeContext().addAccumulator("num-lines", this.counter);
            }

            @Override
            public String map(String s) throws Exception {
                this.counter.add(1);
                return s;
            }
        }).setParallelism(4).writeAsText("test1.txt");

        JobExecutionResult result = env.execute("counter");
        int num = (Integer) result.getAllAccumulatorResults().get("num-lines");
        System.out.println(num);
    }
}
