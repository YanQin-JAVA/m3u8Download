package net.m3u8.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {

    public static String getDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }
}
