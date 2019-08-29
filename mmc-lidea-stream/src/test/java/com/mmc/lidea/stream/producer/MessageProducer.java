/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.stream.producer;

import com.mmc.lidea.stream.context.KafkaConst;
import com.mmc.lidea.util.StringUtil;
import com.mmc.lidea.util.TimeUtil;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.UUID;

import static com.mmc.lidea.util.RandomUtil.getRandomNumberInRange;

/**
 * @author Joey
 * @date 2019/7/14 18:22
 */
public class MessageProducer {

    public static void main(String[] args) throws InterruptedException {


        Properties props = new Properties();
//        props.put("bootstrap.servers", "localhost:9092");
        props.put("bootstrap.servers", "10.204.58.157:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        int totalMessageCount = 1;
        for (int i = 0; i < totalMessageCount; i++) {

            long time = System.currentTimeMillis();
            System.out.println(time);
//            String value = makeMessage(time);
            String value = makeMessage(TimeUtil.timestampToString(time, TimeUtil.yyyyMMddHHmmssSSS));

            System.out.println(value);

            producer.send(new ProducerRecord<>(KafkaConst.TOPIC, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.out.println("Failed to send message with exception " + exception);
                    }
                }
            });
            Thread.sleep(getRandomNumberInRange(1, 10) * 1000);
        }
        producer.close();

    }

    private static String[] interfaces = new String[]{
            "com.fcbox.edms.terminal.api.CabinetServiceFacade",
            "com.fcbox.edms.terminal.api.CabinetServiceAdvanceFacade",
            "com.fcbox.edms.terminal.api.CabinetNetCodeFacade",
            "com.fcbox.edms.terminal.api.CabinetServiceAllSlowFacade"
    };
    private static String[] servers = new String[] {
            "cabinet-base-server",
            "cabinet-conf-server",
            "cabinet-heart-server",
            "cabinet-control-server"
    };

    private static String makeMessage(Object time) {

        // 日志格式
        // time|traceId|type|localIp|remoteIP|appName|serviceName|methodName|args|response|cost|msg|customMsg
        String tpl = StringUtil.format("{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}",
                time, // 时间
                UUID.randomUUID().toString().replace("-", ""),
                getRandomNumberInRange(1, 3), // 1:成功 2：异常
                "10.204.240.74",
                "10.204.240.74",
                getServerName(),
                getInterFaceName(),
                getMethodName(),
                "{\"id\": 888, \"name\": \"zhangsan\"} ",
                "{\"sucess\": true}",
                getRandomNumberInRange(1, 8000),
                "no msg",
                "no data"
                );

        return tpl;
    }

    private static String getMethodName() {

        return "getCabinetInfo";
    }

    private static String getServerName() {
//        return servers[getRandomNumberInRange(0, 0)];
        return servers[getRandomNumberInRange(0, 3)];
    }

    private static String getInterFaceName() {
//        return interfaces[getRandomNumberInRange(0, 0)];
        return interfaces[getRandomNumberInRange(0, 3)];
    }




}
