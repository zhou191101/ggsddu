package com.ggsddu.scala.source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

/**
 * 额外的提高open() 和close()方法
 *
 * @author zhoup
 */
public class MyRichSource extends RichSourceFunction<Long> {
    public void run(SourceContext<Long> sourceContext) throws Exception {

    }

    public void cancel() {

    }

    /**
     * 实现获取链接的代码
     * 该方法只会在开始的时候被调用一次
     *
     * @param parameters
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    /**
     * 实现释放链接的方法
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();
    }
}
