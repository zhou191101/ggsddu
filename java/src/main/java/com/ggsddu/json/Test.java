package com.ggsddu.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {


        Map<String,Object> map = new HashMap<>();

        map.put("aa","aa");
        map.put("bb",null);
        map.put("cc","cc");
        ArrayList<String> a = new ArrayList<>();
        
        a.add("aa");

        a.add("bb");
        a.add("bb");

        System.out.println( a.stream().map(s->map.get(s).toString())
                .collect(Collectors.joining(",")));

    }
}
