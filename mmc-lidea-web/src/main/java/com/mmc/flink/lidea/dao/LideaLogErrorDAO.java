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

import com.mmc.lidea.common.bo.LideaLogErrorDetailBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.flink.lidea.mapper.LideaLogResultsExtractor;
import com.mmc.lidea.util.BytesUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Joey
 * @date 2019/7/26 17:44
 */
@Service("lideaLogErrorDAO")
public class LideaLogErrorDAO {

    @Resource
    private HbaseTemplate hbaseTemplate;


    public List<LideaLogErrorDetailBO> list(List<String> traceIds) {


        return hbaseTemplate.execute(Const.LIDEA_LOG_DEATIL_TABLE, table -> {

            List<LideaLogErrorDetailBO> bos = new ArrayList<>();

            if (traceIds.size() > 1) {

                List<Get> list = new ArrayList<>();

                for (String traceId : traceIds) {

                    Get get = new Get(BytesUtils.toBytes(traceId));

                    list.add(get);
                }

                Result[] rs = table.get(list);

                for (Result r : rs) {
                    LideaLogErrorDetailBO bo = LideaLogResultsExtractor.mapErrorDetail(r);
                    bos.add(bo);
                }
                return bos;

            } else {

                Get get = new Get(BytesUtils.toBytes(traceIds.get(0)));
                Result r = table.get(get);
                if (r.isEmpty()) {
                    return null;
                }
                LideaLogErrorDetailBO bo = LideaLogResultsExtractor.mapErrorDetail(r);

                return Collections.singletonList(bo);
            }
        });

    }
}
