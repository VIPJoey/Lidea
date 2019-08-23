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

import com.mmc.lidea.stream.context.Const;
import com.mmc.lidea.stream.model.LogContent;
import com.mmc.lidea.stream.model.LogContentCount;
import com.mmc.lidea.stream.util.LogAppNameUtil;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import com.mmc.lidea.util.TimeUtil;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Joey
 * @date 2019/8/2 17:18
 */
public class LideaAppSinkFun extends RichSinkFunction<LogContent> {

    private static final long serialVersionUID = 3868272239007101505L;

    private Connection conn = null;

    private BufferedMutator mutator;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        ParameterTool para = (ParameterTool)
                getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        String zkServer = para.getRequired("hbase.zookeeper.quorum");
        String zkPort = para.getRequired("hbase.zookeeper.property.clientPort");
        String tName = para.getRequired("lidea.log.app.name");
        TableName tableName = TableName.valueOf(tName);

        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zkServer);
        config.set("hbase.zookeeper.property.clientPort", zkPort);

        conn = ConnectionFactory.createConnection(config);

        // 连接表
        Table table = conn.getTable(tableName);

        // 设置缓存
        BufferedMutatorParams params = new BufferedMutatorParams(tableName);
        params.writeBufferSize(1024);
        mutator = conn.getBufferedMutator(params);

    }

    @Override
    public void close() throws Exception {
        mutator.flush();
        conn.close();
    }

    @Override
    public void invoke(LogContent bo, Context context) throws IOException {

        // 如果已经存在key，不用重复新增
        if (LogAppNameUtil.exists(bo.appName)) {
            return;
        } else {
            LogAppNameUtil.put(bo.appName);;
        }

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));

        mutator.mutate(put);

    }

    private byte[] makeRowKey(LogContent bo) {
        return BytesUtils.toBytes(MD5Util.encrypt(bo.appName));
    }


}
