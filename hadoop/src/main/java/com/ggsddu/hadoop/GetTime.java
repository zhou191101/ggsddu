package com.ggsddu.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class GetTime {
    public static void main(String[] args) throws IOException {

        URI uri = URI.create("hdfs://localhost:9000/tmp/");
        FileSystem fileSystem = FileSystem.get(uri,new Configuration());

        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/tmp/part-9a64e70b-55b0-4b59-88cb-258f3bd5b1e5"));

        long modificationTime = fileStatus.getModificationTime();

        System.out.println(modificationTime);
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() - modificationTime);

    }
}
