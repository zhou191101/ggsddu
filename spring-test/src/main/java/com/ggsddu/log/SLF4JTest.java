package com.ggsddu.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JTest {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        logger.debug("hello world");
    }
}
