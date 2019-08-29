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

import com.mmc.flink.lidea.common.bo.LideaServiceBO;
import com.mmc.flink.lidea.common.context.Const;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import org.apache.hadoop.hbase.client.Put;

/**
 * 接口实体类.
 *
 * @author Joey
 * @date 2019/8/29 14:30
 */
public class LideaServiceEntry {

    public static Put of(LideaServiceBO bo) {

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));

        return put;
    }

    private static byte[] makeRowKey(LideaServiceBO bo) {

        String base = StringUtil.format("{}{}", bo.appName, MD5Util.encrypt(bo.serviceName));

        return BytesUtils.toBytes(base);
    }
}
