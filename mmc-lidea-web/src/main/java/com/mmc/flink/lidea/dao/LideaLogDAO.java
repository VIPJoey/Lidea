/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.dao;

import com.alibaba.fastjson.JSON;
import com.mmc.lidea.common.bo.LideaLogBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.flink.lidea.dto.LideaLogReq;
import com.mmc.flink.lidea.dto.LideaLogResp;
import com.mmc.flink.lidea.mapper.LideaLogResultsExtractor;
import com.mmc.flink.lidea.util.RowKeyUtils;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import com.mmc.lidea.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Joey
 * @date 2019/7/26 17:44
 */
@Slf4j
@Service("lideaLogDAO")
public class LideaLogDAO {

    @Resource
    private HbaseTemplate hbaseTemplate;

    private byte[] makeRowKey(LideaLogBO bo) {

        String base = StringUtil.format("{}{}{}", bo.getAppName(), bo.getServiceName(), bo.getMethodName());
        long time = TimeUtil.reverseTimeMillis(TimeUtil.stringToLong(bo.getTime())); // 时间倒置

        byte[] md5 = Objects.requireNonNull(MD5Util.encrypt(base)).getBytes(); // 32位
        byte[] rowKey = new byte[Const.MAX_ROW_KEY_LEN + Const.FIX_LONG_LENGTH];

        BytesUtils.writeBytes(rowKey, 0, md5);
        BytesUtils.writeLong(time, rowKey, Const.MAX_ROW_KEY_LEN);
        return rowKey;
    }

    public void put(LideaLogBO bo) {

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"), BytesUtils.toBytes(bo.methodName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("count"), Bytes.toBytes(bo.count));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("avg"), Bytes.toBytes(bo.avg));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("exception"), Bytes.toBytes(bo.exception));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("traceIds"), Bytes.toBytes(bo.traceIds));

        hbaseTemplate.execute(Const.LIDEA_LOG_TABLE, (htable) -> {

            htable.put(put);
            return null;
        });

    }

    public LideaLogResp scan(LideaLogReq req) {

        Scan scan = createScan(req);

        ResultsExtractor<List<LideaLogBO>> extractor = new LideaLogResultsExtractor();
        List<LideaLogBO> list = hbaseTemplate.find(Const.LIDEA_LOG_TABLE, scan, extractor);

        LideaLogResp resp = new LideaLogResp();
        resp.setData(list);
        resp.setAppName(req.getAppName());
        resp.setServiceName(req.getServiceName());
        resp.setMethodName(req.getMethodName());

        return resp;
    }

    private Scan createScan(LideaLogReq bo) {

        Scan scan = new Scan();
        scan.setCaching(60 * 24);

        String base = StringUtil.format("{}{}{}", bo.getAppName(), bo.getServiceName(), bo.getMethodName());
        byte[] fixedBytes = Objects.requireNonNull(MD5Util.encrypt(base)).getBytes(); // 32位

        byte[] traceIndexStartKey = getApplicationTraceIndexRowKey(fixedBytes, bo.getFrom());

        byte[] traceIndexEndKey = getApplicationTraceIndexRowKey(fixedBytes, bo.getTo());

        // start key is replaced by end key because key has been reversed

        scan.withStartRow(traceIndexStartKey);
        scan.withStartRow(traceIndexEndKey);

        scan.addFamily(Const.LIDEA_LOG_FEMILY);
        scan.setId("LideaLogScan");

        return scan;

    }

    private byte[] getApplicationTraceIndexRowKey(byte[] fixedBytes, long timestamp) {

        return RowKeyUtils.concatFixedByteAndLong(fixedBytes, Const.MAX_ROW_KEY_LEN, TimeUtil.reverseTimeMillis(timestamp));

    }

    public LideaLogResp get(LideaLogReq req) {

        log.info("req : {}", JSON.toJSONString(req));

        LideaLogResp resp = new LideaLogResp();
        resp.setAppName(req.getAppName());
        resp.setServiceName(req.getServiceName());
        resp.setMethodName(req.getMethodName());

        List<LideaLogBO> data = hbaseTemplate.execute(Const.LIDEA_LOG_TABLE, (table) -> {

            String base = StringUtil.format("{}{}{}", req.getAppName(), req.getServiceName(), req.getMethodName());
            byte[] fixedBytes = Objects.requireNonNull(MD5Util.encrypt(base)).getBytes(); // 32位

            byte[] rowKey = getApplicationTraceIndexRowKey(fixedBytes, req.getFrom());
            Get get = new Get(rowKey);

            Result result = table.get(get);

            LideaLogBO bo = LideaLogResultsExtractor.map(result);

            return Collections.singletonList(bo);
        });

        resp.setData(data);

        return resp;
    }
}
