/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.common.entry;

import com.mmc.flink.lidea.common.bo.LideaMethodBO;
import com.mmc.flink.lidea.common.context.Const;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import org.apache.hadoop.hbase.client.Put;

/**
 * @author Joey
 * @date 2019/8/29 15:01
 */
public class LideaMethodEntry {

    public static Put of(LideaMethodBO bo) {

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"), BytesUtils.toBytes(bo.methodName));

        return put;
    }

    private static byte[] makeRowKey(LideaMethodBO bo) {
        String base = StringUtil.format("{}{}", MD5Util.encrypt(bo.appName + bo.serviceName), bo.methodName);
        return BytesUtils.toBytes(base);
    }

}
