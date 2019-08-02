/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.service;

import com.mmc.flink.lidea.bo.LideaLogBO;
import com.mmc.flink.lidea.context.Const;
import com.mmc.flink.lidea.dto.LideaLogResp;
import com.mmc.flink.lidea.dto.LideaLogReq;
import com.mmc.flink.lidea.mapper.LideaLogResultsExtractor;
import com.mmc.flink.lidea.util.*;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Joey
 * @date 2019/7/26 17:44
 */
@Service("lideaLogDAO")
public class LideaLogDAO {

    @Resource
    private HbaseTemplate hbaseTemplate;

    public void put(LideaLogBO bo) {

        String base = StringUtil.format("{}{}{}", bo.getLapp(), bo.getLinterface(), bo.getLmethod());
        long time = TimeUtil.reverseTimeMillis(TimeUtil.convertTimeToLong(bo.getLtime())); // 时间倒置

        byte[] md5 = Objects.requireNonNull(MD5Util.encrypt(base)).getBytes(); // 32位
        byte[] rowKey = new byte[Const.MAX_ROW_KEY_LEN + Const.FIX_LONG_LENGTH];

        BytesUtils.writeBytes(rowKey, 0, md5);
        BytesUtils.writeLong(time, rowKey, Const.MAX_ROW_KEY_LEN);

        Put put = new Put(rowKey);
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("ltime"), BytesUtils.toBytes(bo.getLtime()));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("lapp"), BytesUtils.toBytes(bo.getLapp()));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("linterface"), BytesUtils.toBytes(bo.getLinterface()));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("lmethod"), BytesUtils.toBytes(bo.getLmethod()));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("lcount"), Bytes.toBytes(bo.getLcount()));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("lavg"), Bytes.toBytes(bo.getLavg()));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("lexception"), Bytes.toBytes(bo.getLexception()));

        hbaseTemplate.execute(Const.LIDEA_LOG_TABLE, (htable) -> {

            htable.put(put);
            return null;
        });

    }

    public LideaLogResp scan(LideaLogReq req) {

        Scan scan = createScan(req);

        ResultsExtractor<List<LideaLogBO>> extractor = new LideaLogResultsExtractor();
        List<LideaLogBO> list = hbaseTemplate.find(Const.LIDEA_LOG_TABLE, scan, extractor);

        LideaLogResp range = new LideaLogResp();
        range.setData(list);
        range.setLapp(req.getLapp());
        range.setLinterface(req.getLinterface());
        range.setLmethod(req.getLmethod());

        return range;
    }

    private Scan createScan(LideaLogReq bo) {

        Scan scan = new Scan();
        scan.setCaching(60 * 24);

        String base = StringUtil.format("{}{}{}", bo.getLapp(), bo.getLinterface(), bo.getLmethod());
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
}
