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

import com.alibaba.fastjson.JSON;
import com.mmc.lidea.common.bo.LideaAppBO;
import com.mmc.lidea.common.bo.LideaLogBO;
import com.mmc.lidea.common.bo.LideaMethodBO;
import com.mmc.lidea.common.bo.LideaServiceBO;
import com.mmc.lidea.util.RandomUtil;
import com.mmc.lidea.util.TimeUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Joey
 * @date 2019/8/30 15:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoTest {

    private final String APP_NAME = "cabinet-base-server";
    private final String SERVICE_NAME = "com.fcbox.edms.terminal.api.CabinetServiceFacade";
    private final String METHOD_NAME = "getCabinetInfo";

    @Resource
    private LideaAppDAO lideaAppDAO;
    @Resource
    private LideaServiceDAO lideaServiceDAO;
    @Resource
    private LideaMethodDAO lideaMethodDAO;
    @Resource
    private LideaLogDAO lideaLogDAO;

    @Test
    public void testAll() {

        testPutApp(APP_NAME);
        testPutApp("just_test");
        testPutService();
        testPutMethod();
        testPutLog();
    }

    private void testPutApp(String appName) {

        LideaAppBO bo = new LideaAppBO();
        bo.appName = appName;
        bo.time = TimeUtil.dateToString(LocalDateTime.now());

        bo = lideaAppDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));

    }

    private void testPutService() {

        LideaServiceBO bo = new LideaServiceBO();
        bo.appName = APP_NAME;
        bo.serviceName = SERVICE_NAME;

        bo = lideaServiceDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));

    }

    private void testPutMethod() {

        LideaMethodBO bo = new LideaMethodBO();
        bo.setTime(TimeUtil.dateToString(LocalDateTime.now()));
        bo.setAppName(APP_NAME);
        bo.setServiceName(SERVICE_NAME);
        bo.setMethodName(METHOD_NAME);
        bo.setCount("100");

        bo = lideaMethodDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));
    }

    private void testPutLog() {

        // 测试插入一条记录
        LideaLogBO bo = new LideaLogBO();
        bo.setTime(TimeUtil.dateToString(LocalDateTime.now()));
        bo.setAppName(APP_NAME);
        bo.setServiceName(SERVICE_NAME);
        bo.setMethodName(METHOD_NAME);
        bo.setCount(RandomUtil.getRandomNumberInRange(1, 100));
        bo.setAvg(RandomUtil.getRandomNumberInRange(100, 999));
        bo.setException(RandomUtil.getRandomNumberInRange(1, 100));
        bo.setTraceIds(UUID.randomUUID().toString().replace("-", ""));

        lideaLogDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));
    }
}
