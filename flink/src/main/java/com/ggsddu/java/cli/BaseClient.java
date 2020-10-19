package com.ggsddu.java.cli;


import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;

import java.io.File;

public abstract class BaseClient {
    protected Configuration getConfiguration() {
        String confFile = BaseClient.class.getResource("/testconfig/flink-conf.yaml").getFile();

        return GlobalConfiguration.loadConfiguration(new File(confFile).getAbsoluteFile().getParent());
    }

}
