/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.test;

import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.TimeUtil;
import org.junit.Test;

/**
 * @author Joey
 * @date 2019/8/5 23:49
 */
public class ByteTest {


    @Test
    public void test() {


        byte[] bb = hexStringToBytes("7FFFFE939DFBECAF");

        long l = BytesUtils.bytesToLong(bb, 0);
        System.out.println(l);

        System.out.println(TimeUtil.reverseTimeMillis(9223370471842245807L));

        System.out.println(TimeUtil.timestampToString(1565012530000L, TimeUtil.yyyyMMddHHmmssSSS));
        System.out.println(TimeUtil.timestampToString(1565012530000L));

    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


}
