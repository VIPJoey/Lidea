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

import com.mmc.flink.lidea.common.bo.LideaMethodBO;
import com.mmc.flink.lidea.common.context.Const;
import com.mmc.flink.lidea.common.entry.LideaMethodEntry;
import com.mmc.flink.lidea.dto.LideaMethodReq;
import com.mmc.flink.lidea.mapper.LideaMethodResultsExtractor;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joey
 * @date 2019/8/6 18:29
 */
@Service("lideaMethodDAO")
public class LideaMethodDAO {

    @Resource
    private HbaseTemplate hbaseTemplate;

    public LideaMethodBO put(LideaMethodBO bo) {

        Put put = LideaMethodEntry.of(bo);

        return hbaseTemplate.execute(Const.LIDEA_METHOD_TABLE, (htable) -> {

            htable.put(put);
            return bo;

        });

    }

    public List<LideaMethodBO> scan(LideaMethodReq req) {

        Scan scan = new Scan();
        scan.setCaching(50);
        scan.addFamily(Const.LIDEA_LOG_FEMILY);
        scan.setFilter(new PrefixFilter(BytesUtils.toBytes(MD5Util.encrypt(req.getAppName() + req.getServiceName()))));

        return hbaseTemplate.find(Const.LIDEA_METHOD_TABLE, scan, new LideaMethodResultsExtractor());
    }
}
