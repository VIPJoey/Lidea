/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.stream.job;

import com.mmc.flink.lidea.common.context.KafkaConst;
import com.mmc.lidea.stream.Bootstrap;
import com.mmc.lidea.stream.flink.LideaServiceSinkFun;
import com.mmc.lidea.stream.flink.LogContentFilter;
import com.mmc.lidea.stream.flink.LogContentSplitter;
import com.mmc.lidea.stream.flink.MessageWaterEmitter;
import com.mmc.lidea.stream.model.LogContent;
import com.mmc.lidea.stream.util.LogServiceNameUtil;
import com.mmc.lidea.util.MD5Util;
import com.mmc.lidea.util.StringUtil;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Joey
 * @date 2019/8/4 16:39
 */
public class LideaServiceJob {


    public static void main(String[] args) throws Exception {


        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000); // 非常关键，一定要设置启动检查点！！
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1); // 这个不需要太多资源

        String confName = "lidea.properties";
        InputStream in = Bootstrap.class.getClassLoader().getResourceAsStream(confName);
        ParameterTool parameterTool = ParameterTool.fromPropertiesFile(in);
        env.getConfig().setGlobalJobParameters(parameterTool);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", parameterTool.get("kafka.bootstrap.servers", "localhost:9092"));
        props.setProperty("group.id", "lidea-app-group");

        FlinkKafkaConsumer010<String> consumer =
                new FlinkKafkaConsumer010<>(KafkaConst.TOPIC, new SimpleStringSchema(), props);
        consumer.assignTimestampsAndWatermarks(new MessageWaterEmitter()); // 水位

        // 分离出日志格式
        DataStream<LogContent> mapStream = env.addSource(consumer)
                .filter(new LogContentFilter())
                .map(new LogContentSplitter());

        // 写入APP数据
        addBaseJob(mapStream);

        env.execute("Record the interface name.");

    }

    private static void addBaseJob(DataStream<LogContent> mapStream) {

        mapStream.filter(value -> {
            String key = MD5Util.encrypt(StringUtil.format("{}{}",
                    value.appName, value.serviceName));

            return !LogServiceNameUtil.exists(key);

        }).keyBy("traceId").timeWindow(Time.seconds(10));

        mapStream.addSink(new LideaServiceSinkFun());
    }

}
