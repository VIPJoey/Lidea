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

import com.mmc.lidea.common.bo.LideaServiceBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.lidea.common.entry.LideaServiceEntry;
import com.mmc.flink.lidea.dto.LideaServiceReq;
import com.mmc.flink.lidea.mapper.LideaServiceResultsExtractor;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Joey
 * @date 2019/8/29 14:31
 */
@Service("lideaServiceDAO")
public class LideaServiceDAO {

    @Resource
    private HbaseTemplate hbaseTemplate;

    public LideaServiceBO put(LideaServiceBO bo) {

        Put put = LideaServiceEntry.of(bo);

        return hbaseTemplate.execute(Const.LIDEA_SERVICE_TABLE, (htable) -> {

            htable.put(put);
            return bo;

        });
    }

    public List<LideaServiceBO> scan(LideaServiceReq req) {

        Scan scan = new Scan();
        scan.setCaching(50);
        scan.addFamily(Const.LIDEA_LOG_FEMILY);
        scan.setFilter(new PrefixFilter(req.getAppName().getBytes()));

        return hbaseTemplate.find(Const.LIDEA_SERVICE_TABLE, scan, new LideaServiceResultsExtractor());
    }
}
