package com.mmc.lidea.util;

import org.junit.Assert;
import org.junit.Test;

public class TimeUtilTest {

    @Test
    public void test() {


        long time = System.currentTimeMillis();

        String stime = TimeUtil.timestampToString(time, TimeUtil.yyyyMMddHHmmssSSS);
        long ntime = TimeUtil.stringToLong(stime, TimeUtil.yyyyMMddHHmmssSSS);

        System.out.println(StringUtil.format("{} {} {}", time, stime, ntime));
        Assert.assertEquals(time, ntime);

    }

    @Test
    public void testEqual() {


        long timestamp = 1565057370000L;

        String timestr = "2019-08-06 10:09:30";

        long ntime = TimeUtil.stringToLong(timestr);
        Assert.assertEquals(timestamp, ntime);

        String ntimestr = TimeUtil.timestampToString(timestamp);
        Assert.assertEquals(timestr, ntimestr);
    }

}
