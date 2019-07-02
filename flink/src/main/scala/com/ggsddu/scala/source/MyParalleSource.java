package com.ggsddu.scala.source;

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

/**
 * @author zhoup
 */
public class MyParalleSource implements ParallelSourceFunction<Long> {

    public void run(SourceContext<Long> sourceContext) throws Exception {

    }

    public void cancel() {

    }
}
