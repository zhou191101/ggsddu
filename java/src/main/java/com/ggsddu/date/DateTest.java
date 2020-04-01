package com.ggsddu.date;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTest {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);
        System.out.println(simpleDateFormat.format(new Date()));
    }
}
