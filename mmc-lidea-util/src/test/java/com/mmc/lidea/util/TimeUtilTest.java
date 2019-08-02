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

}
