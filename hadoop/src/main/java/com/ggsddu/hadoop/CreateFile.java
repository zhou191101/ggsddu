package com.ggsddu.hadoop;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;


import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class CreateFile {
    public static void main(String[] args) throws IOException, InterruptedException {

        URI uri = URI.create("hdfs://localhost:9000/tmp/");
        FileSystem fileSystem = FileSystem.get(uri,new Configuration());

        FSDataOutputStream fs = fileSystem.create(new Path("/tmp/part-" + UUID.randomUUID()));


        fs.writeUTF("aaaa");

        fs.hflush();
        Thread.sleep(100000);

       // fs.close();
       // fileSystem.close();
    }
}
