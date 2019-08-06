/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.mapper;

import com.mmc.flink.lidea.bo.LideaLogBO;
import com.mmc.flink.lidea.bo.LideaLogErrorDetailBO;
import com.mmc.flink.lidea.context.Const;
import com.mmc.lidea.util.BytesUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joey
 * @date 2019/7/26 21:11
 */
public class LideaLogResultsExtractor implements ResultsExtractor<List<LideaLogBO>> {

    /**
     * Implementations must implement this method to process the entire {@link ResultScanner}.
     *
     * @param results {@link ResultScanner} to extract data from. Implementations should not close this; it will be closed
     *                automatically by the calling {@link HbaseTemplate}
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws Exception if an Hbase exception is encountered
     */
    @Override
    public List<LideaLogBO> extractData(ResultScanner results) throws Exception {

        List<LideaLogBO> rs = new ArrayList<>();
        for (Result result : results) {
            LideaLogBO bo = map(result);
            rs.add(bo);
        }

        return rs;
    }

    public static LideaLogBO map(Result result) {
        LideaLogBO bo = new LideaLogBO();
        bo.setTime(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"))));
        bo.setAppName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"))));
        bo.setServiceName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"))));
        bo.setMethodName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"))));
        bo.setCount(Bytes.toLong(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("count"))));
        bo.setAvg(Bytes.toInt(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("avg"))));
        bo.setException(Bytes.toInt(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("exception"))));
        bo.setTraceIds(Bytes.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("traceIds"))));
        return bo;
    }

    public static LideaLogErrorDetailBO mapErrorDetail(Result result) {
        LideaLogErrorDetailBO bo = new LideaLogErrorDetailBO();
        bo.setTraceId(BytesUtils.toString(result.getRow()));
        bo.setTime(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"))));
        bo.setLocalIp(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("localIp"))));
        bo.setRemoteIp(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("remoteIp"))));
        bo.setAppName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"))));
        bo.setServiceName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"))));
        bo.setMethodName(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"))));
        bo.setArgs(Bytes.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("args"))));
        bo.setResponse(BytesUtils.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("response"))));
        bo.setCost(Bytes.toInt(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("cost"))));
        bo.setMsg(Bytes.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("msg"))));
        bo.setCustomMsg(Bytes.toString(result.getValue(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("customMsg"))));
        return bo;
    }
}
