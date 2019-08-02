/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Joey
 * @date 2019/7/20 20:19
 */
public class TimeUtil {

    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 时间戳转字符串.
     *
     * @param time
     * @return
     */
    public static String timestampToString(Long time) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * 时间戳转字符串.
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String timestampToString(Long time, String pattern) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * 字符串转时间戳.
     *
     * @param ltime
     * @return
     */
    public static long stringToLong(String ltime) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
        LocalDateTime parse = LocalDateTime.parse(ltime, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 字符串转时间戳.
     *
     * @param ltime
     * @param pattern
     * @return
     */
    public static long stringToLong(String ltime, String pattern) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime parse = LocalDateTime.parse(ltime, ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 翻转时间.
     *
     * @param currentTimeMillis
     * @return
     */
    public static long reverseTimeMillis(long currentTimeMillis) {
        return Long.MAX_VALUE - currentTimeMillis;
    }

    /**
     * 时间转字符串.
     *
     * @param now
     * @return
     */
    public static String dateToString(LocalDateTime now) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
        return ftf.format(now);
    }
}
