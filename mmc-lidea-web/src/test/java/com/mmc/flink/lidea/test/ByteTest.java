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

import com.mmc.flink.lidea.util.MD5Util;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Joey
 * @date 2019/7/26 19:36
 */
public class ByteTest {

    @Test
    public void test() {


        String base = "cabinet-base-servercom.fcbox.edms.terminal.api.CabinetServiceFacadegetCabinetInfo";

        String md5 = MD5Util.encrypt(base);

        byte[] ret = Objects.requireNonNull(md5).getBytes();

        System.out.println(ret.length);
        System.out.println(Arrays.toString(ret));

    }

}
