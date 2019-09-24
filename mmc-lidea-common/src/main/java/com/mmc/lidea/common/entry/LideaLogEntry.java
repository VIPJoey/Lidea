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

import com.mmc.lidea.common.bo.LideaLogBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import com.mmc.lidea.util.TimeUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Objects;

/**
 * 日志内容实体类.
 * @author Joey
 * @date 2019/9/4 19:40
 */
public class LideaLogEntry {

    public static Put of(LideaLogBO bo) {

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"), BytesUtils.toBytes(bo.methodName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("count"), Bytes.toBytes(bo.count));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("avg"), Bytes.toBytes(bo.avg));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("exception"), Bytes.toBytes(bo.exception));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("traceIds"), Bytes.toBytes(bo.traceIds));

        return put;
    }

    private static byte[] makeRowKey(LideaLogBO bo) {

        String base = StringUtil.format("{}{}{}", bo.getAppName(), bo.getServiceName(), bo.getMethodName());
        long time = TimeUtil.stringToLong(bo.getTime());

        return makeRowKey(MD5Util.encrypt(base), time);
    }

    /**
     * 行键.
     * @param md5 应用+服务+方法 的md5
     * @param timestamp 时间
     *
     * @return
     */
    public static byte[] makeRowKey(String md5, long timestamp) {

        byte[] base = BytesUtils.toBytes(md5); // 32位
        byte[] rowKey = new byte[Const.MAX_ROW_KEY_LEN + Const.FIX_LONG_LENGTH];

        long time = TimeUtil.reverseTimeMillis(timestamp); // 时间倒置

        BytesUtils.writeBytes(rowKey, 0, base);
        BytesUtils.writeLong(time, rowKey, Const.MAX_ROW_KEY_LEN);
        return rowKey;
    }
}
