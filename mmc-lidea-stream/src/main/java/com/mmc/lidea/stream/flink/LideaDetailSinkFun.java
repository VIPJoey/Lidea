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
import com.mmc.lidea.util.BytesUtils;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Joey
 * @date 2019/8/4 17:16
 */
public class LideaDetailSinkFun extends RichSinkFunction<LogContent> {

    private static final long serialVersionUID = 8692984903123681090L;


    private Connection conn = null;
    private Table table = null;

    private static TableName tableName;

    private static final String click = "click";
    private BufferedMutatorParams params;
    private BufferedMutator mutator;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        ParameterTool para = (ParameterTool)
                getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        String zkServer = para.getRequired("hbase.zookeeper.quorum");
        String zkPort = para.getRequired("hbase.zookeeper.property.clientPort");
        String tName = para.getRequired("lidea.log.detail.name");
        tableName = TableName.valueOf(tName);

        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zkServer);
        config.set("hbase.zookeeper.property.clientPort", zkPort);

        conn = ConnectionFactory.createConnection(config);
        // 连接表
        table = conn.getTable(tableName);

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
    public void invoke(LogContent bo, Context context) throws IOException {


        byte[] rowKey =  Objects.requireNonNull(bo.traceId.getBytes()); // 直接用traceId作为rowKey


        Put put = new Put(rowKey);
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"), BytesUtils.toBytes(bo.methodName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("localIp"), BytesUtils.toBytes(bo.localIp));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("remoteIp"), BytesUtils.toBytes(bo.remoteIp));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("args"), BytesUtils.toBytes(bo.args));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("response"), BytesUtils.toBytes(bo.response));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("cost"), Bytes.toBytes(bo.cost));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("msg"), BytesUtils.toBytes(bo.msg));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("customMsg"), BytesUtils.toBytes(bo.customMsg));

        mutator.mutate(put);


    }
}
