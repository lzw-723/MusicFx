/*
 * @Author: lzw-723 
 * @Date: 2020-03-25 18:57:41 
 * @Last Modified by: lzw-723
 * @Last Modified time: 2020-03-25 18:59:31
 */
package io.github.lzw.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeFormater
 */
public class TimeFormatter {

    public static String format(long time) {
        return new SimpleDateFormat("mm:ss").format(new Date(time));
    }

    public static boolean isDaytime(Date date) {
        return Integer.valueOf(new SimpleDateFormat("hh").format(date)) < 18;
    }

    public static String getDay(Date date) {
        return new SimpleDateFormat("MM-dd").format(date);
    }
}