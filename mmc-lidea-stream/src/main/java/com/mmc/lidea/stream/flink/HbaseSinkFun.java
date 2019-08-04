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
import com.mmc.lidea.stream.model.LogContentCount;
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
import java.util.Objects;

/**
 * @author Joey
 * @date 2019/8/2 17:18
 */
public class HbaseSinkFun extends RichSinkFunction<LogContentCount> {

    private static final long serialVersionUID = 3868272239007101505L;

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
        String tName = para.getRequired("hbase.tableName");
        tableName = TableName.valueOf(tName);

        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zkServer);
        config.set("hbase.zookeeper.property.clientPort", zkPort);

        conn = ConnectionFactory.createConnection(config);
//        Admin admin = conn.getAdmin();
//        admin.listTableNames();
//        if (!admin.tableExists(tableName)) {
//            HTableDescriptor tableDes = new HTableDescriptor(tableName);
//
//            tableDes.addFamily(new HColumnDescriptor(click).setMaxVersions(3));
//
//            System.out.println("create table");
//            admin.flush(tableName);
//        }
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
    public void invoke(LogContentCount bo, Context context) throws IOException {


        String base = StringUtil.format("{}{}{}", bo.appName, bo.serviceName, bo.methodName);
        long time = TimeUtil.reverseTimeMillis(TimeUtil.stringToLong(bo.getTime())); // 时间倒置

        byte[] md5 = Objects.requireNonNull(MD5Util.encrypt(base)).getBytes(); // 32位
        byte[] rowKey = new byte[Const.MAX_ROW_KEY_LEN + Const.FIX_LONG_LENGTH];

        BytesUtils.writeBytes(rowKey, 0, md5);
        BytesUtils.writeLong(time, rowKey, Const.MAX_ROW_KEY_LEN);

        Put put = new Put(rowKey);
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("time"), BytesUtils.toBytes(bo.time));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("appName"), BytesUtils.toBytes(bo.appName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("serviceName"), BytesUtils.toBytes(bo.serviceName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("methodName"), BytesUtils.toBytes(bo.methodName));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("count"), Bytes.toBytes(bo.count));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("avg"), Bytes.toBytes(bo.avg));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("exception"), Bytes.toBytes(bo.exception));
        put.addColumn(Const.LIDEA_LOG_FEMILY, BytesUtils.toBytes("traceIds"), Bytes.toBytes(bo.traceIds));

        mutator.mutate(put);


    }

}
