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
import com.mmc.flink.lidea.common.bo.LideaLogBO;
import com.mmc.flink.lidea.dto.LideaLogReq;
import com.mmc.flink.lidea.dto.LideaLogResp;
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
 * @date 2019/7/26 17:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LideaLogDAOTest {

    @Resource
    private LideaLogDAO lideaLogDAO;

    @Test
    public void testPut() {

        // 测试插入一条记录
        LideaLogBO bo = new LideaLogBO();
        bo.setTime(TimeUtil.dateToString(LocalDateTime.now()));
        bo.setAppName("cabinet-base-server");
        bo.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        bo.setMethodName("getCabinetInfo");
        bo.setCount(RandomUtil.getRandomNumberInRange(1, 100));
        bo.setAvg(RandomUtil.getRandomNumberInRange(100, 999));
        bo.setException(RandomUtil.getRandomNumberInRange(1, 100));
        bo.setTraceIds(UUID.randomUUID().toString().replace("-", ""));

        lideaLogDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));
    }

    @Test
    public void testScan() {

        LideaLogReq req = new LideaLogReq();

        req.setAppName("cabinet-base-server");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        req.setMethodName("getCabinetInfo");

        req.setFrom(TimeUtil.stringToLong("2019-07-20 00:00:00"));
        req.setTo(TimeUtil.stringToLong("2019-08-29 23:00:00"));

        LideaLogResp resp = lideaLogDAO.scan(req);
        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(resp));
    }

    @Test
    public void testGet() {

        LideaLogReq req = new LideaLogReq();

        req.setAppName("cabinet-base-server");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        req.setMethodName("getCabinetInfo");

        req.setFrom(TimeUtil.stringToLong("2019-08-05 14:26:39"));
        req.setTo(TimeUtil.stringToLong("2019-08-05 14:26:39"));

        LideaLogResp resp = lideaLogDAO.get(req);
        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(resp));

    }

    @Test
    public void testGet1() {

        LideaLogReq req = new LideaLogReq();

        req.setAppName("cabinet-base-server");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        req.setMethodName("getCabinetInfo");

        System.out.println(TimeUtil.timestampToString(1565266170000L, TimeUtil.yyyyMMddHHmmssSSS));

        req.setFrom(1565266170000L);
        req.setTo(1565266170000L);

        LideaLogResp resp = lideaLogDAO.get(req);
        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(resp));

    }

}
