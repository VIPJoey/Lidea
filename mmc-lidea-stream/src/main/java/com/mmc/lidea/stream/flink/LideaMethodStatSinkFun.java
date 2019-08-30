/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.stream.flink;

import com.mmc.lidea.common.bo.LideaMethodBO;
import com.mmc.lidea.common.context.Const;
import com.mmc.lidea.common.entry.LideaMethodEntry;
import com.mmc.lidea.stream.model.LogContentCount;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.StringUtil;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Joey
 * @date 2019/8/2 17:18
 */
public class LideaMethodStatSinkFun extends RichSinkFunction<LogContentCount> {

    private static final long serialVersionUID = 3868272239007101505L;

    private Connection conn = null;
    private Table table = null;

    private static TableName tableName;

    private BufferedMutatorParams params;
    private BufferedMutator mutator;

    private ConcurrentHashMap<String, Long> countCache = new ConcurrentHashMap<>();

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        ParameterTool para = (ParameterTool)
                getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        String zkServer = para.getRequired("hbase.zookeeper.quorum");
        String zkPort = para.getRequired("hbase.zookeeper.property.clientPort");
        String tName = para.getRequired("lidea.log.method.name");
        tableName = TableName.valueOf(tName);

        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zkServer);
        config.set("hbase.zookeeper.property.clientPort", zkPort);

        conn = ConnectionFactory.createConnection(config);

        // 连接表
//        table = conn.getTable(tableName);

        // 设置缓存
        params = new BufferedMutatorParams(tableName);
        params.writeBufferSize(1024);
        mutator = conn.getBufferedMutator(params);

    }

    @Override
    public void close() throws Exception {
        mutator.flush();
        conn.close();
    }

    @Override
    public void invoke(LogContentCount bo, Context context) throws IOException {

        LideaMethodBO dto = new LideaMethodBO();
        dto.appName = bo.appName;
        dto.serviceName = bo.serviceName;
        dto.methodName = bo.methodName;

        byte[] rowKey = LideaMethodEntry.makeRowKey(dto);
        String base = StringUtil.format("{}{}{}", bo.appName, bo.serviceName, bo.methodName);
        Put put = new Put(rowKey);

        long count = bo.count;

        // 如果缓存里没有全局统计值，到hbase里找
        if (!countCache.containsKey(base)) {
            Get get = new Get(rowKey);
            table = conn.getTable(tableName);
            Result result = table.get(get);
            if (!result.isEmpty()) {
                // 放入缓存
                LideaMethodBO src = LideaMethodEntry.map(result);
                count += Long.valueOf(src.count);
            }
        } else {

            // 累加
            count += countCache.get(base);
        }

        // 重新设置缓存
        countCache.put(base, count);

        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("count"), BytesUtils.toBytes(String.valueOf(count)));

        mutator.mutate(put);


    }

}
