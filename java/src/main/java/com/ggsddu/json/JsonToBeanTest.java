package com.ggsddu.json;

import com.alibaba.fastjson.JSONObject;

public class JsonToBeanTest {
    public static void main(String[] args) {
        String json = "{\"age\":\"10\",\"username\":\"zhangsan\"}";
        JSONObject object = JSONObject.parseObject(json);
        Person person = JSONObject.toJavaObject(object, Person.class);
       // person.setAddress("aaaa");
        System.out.println(person.getAddress());


        JSONObject object1 = JSONObject.parseObject(json);
        System.out.println(object1.getOrDefault("address","").toString());
    }



}
