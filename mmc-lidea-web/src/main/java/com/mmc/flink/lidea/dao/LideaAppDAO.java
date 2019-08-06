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

import com.mmc.flink.lidea.bo.LideaAppBO;
import com.mmc.flink.lidea.context.Const;
import com.mmc.flink.lidea.mapper.LideaAppResultsExtractor;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joey
 * @date 2019/8/6 18:29
 */
@Service("lideaAppDAO")
public class LideaAppDAO {

    @Resource
    private HbaseTemplate hbaseTemplate;

    public LideaAppBO put(LideaAppBO bo) {

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));

        return hbaseTemplate.execute(Const.LIDEA_APP_TABLE, (htable) -> {

            htable.put(put);
            return bo;

        });

    }

    private byte[] makeRowKey(LideaAppBO bo) {
        return BytesUtils.toBytes(MD5Util.encrypt(bo.appName));
    }

    public List<LideaAppBO> scan() {

        Scan scan = new Scan();
        scan.setCaching(30);
        scan.addFamily(Const.LIDEA_LOG_FEMILY);

        return hbaseTemplate.find(Const.LIDEA_APP_TABLE, scan, new LideaAppResultsExtractor());
    }
}
