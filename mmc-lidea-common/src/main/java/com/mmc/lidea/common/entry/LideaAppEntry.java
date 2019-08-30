/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.common.entry;

import com.mmc.lidea.common.bo.LideaAppBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import org.apache.hadoop.hbase.client.Put;

/**
 * 应用名称实体操作类.
 * @author Joey
 * @date 2019/8/29 10:48
 */
public class LideaAppEntry {

    public static Put of(LideaAppBO bo) {


        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));

        return put;
    }


    private static byte[] makeRowKey(LideaAppBO bo) {
        return BytesUtils.toBytes(MD5Util.encrypt(bo.appName));
    }
}
