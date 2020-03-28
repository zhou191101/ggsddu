package com.ggsddu.java.cli;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FLinkClient {

    public static void main(String[] args) {

        InputStream inputStream = null;
        try {
            Process process = Runtime.getRuntime().exec("/Users/zhoupeng/Downloads/bigdata/flink/bin/flink " +
                    "run -m yarn-cluster /Users/zhoupeng/Downloads/bigdata/flink/examples/batch/WordCount.jar"
            );
            process.waitFor();
            inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String result = null;
            while ((result = reader.readLine()) != null) {
                System.out.println(result);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
