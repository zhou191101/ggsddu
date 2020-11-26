package com.ggsddu.regex;

public class ReplaceFirstTest {
    public static void main(String[] args) {

        String test = "aaa_bbb_ccc_ddd.dasad_dsds";

        System.out.println(test.replaceFirst("_","."));
    }
}
