package com.ggsddu.java.cli;

import org.apache.flink.client.cli.AbstractCustomCommandLine;
import org.apache.flink.client.cli.DefaultCLI;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;

import java.io.File;

public abstract class BaseClient {
    protected Configuration getConfiguration() {
        String confFile = BaseClient.class.getResource("/testconfig/flink-conf.yaml").getFile();

        return GlobalConfiguration.loadConfiguration(new File(confFile).getAbsoluteFile().getParent());
    }

    static AbstractCustomCommandLine getCli(Configuration configuration) {
        return new DefaultCLI(configuration);
    }
}
