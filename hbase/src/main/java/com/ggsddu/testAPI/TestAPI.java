package com.ggsddu.testAPI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * DDL
 * 1.判断表是否存在
 * 2.创建表
 * 3.创建namespace
 * 4.删除表
 * DML
 * 1.插入数据
 * 2.查询数据
 * 2.1 get
 * 2.2 scan
 * 3.删除数据
 */
public class TestAPI {

    private static Connection connection = null;
    private static Admin admin = null;

    static {
        try {
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", "192.168.124.12");
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(isExists("a"));

        close();
    }

    private static boolean isExists(String tableName) throws IOException {

        return admin.tableExists(TableName.valueOf(tableName));
    }

    private static void createTable(String tableName, List<String> columnFamilies) throws IOException {
        if (columnFamilies.isEmpty()) {
            System.out.println("Please set columnFamilies..");
            return;
        }
        if (isExists(tableName)) {
            System.out.println(tableName + " 已存在..");
            return;
        }

        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (String columnFamily : columnFamilies) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(columnFamily);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        admin.createTable(hTableDescriptor);
    }

    private static void close() {
        if (Objects.nonNull(admin)) {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
