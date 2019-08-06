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
import com.mmc.lidea.stream.util.LogAppNameUtil;
import com.mmc.lidea.stream.util.LogMethodNameUtil;
import com.mmc.lidea.util.BytesUtils;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * @author Joey
 * @date 2019/8/2 17:18
 */
public class LideaMethodSinkFun extends RichSinkFunction<LogContent> {

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
        String tName = para.getRequired("lidea.log.method.name");
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

        // 过滤一下
        String key = MD5Util.encrypt(StringUtil.format("{}{}{}", bo.appName, bo.serviceName, bo.methodName));
        if (LogMethodNameUtil.exist(key)) {
            return;
        }
        LogMethodNameUtil.put(key);

        Put put = new Put(makeRowKey(bo));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"), BytesUtils.toBytes(bo.methodName));


        mutator.mutate(put);

    }

    private byte[] makeRowKey(LogContent bo) {
        String base = StringUtil.format("{}{}", bo.appName, MD5Util.encrypt(bo.serviceName + bo.methodName));
        return BytesUtils.toBytes(base);
    }


}
