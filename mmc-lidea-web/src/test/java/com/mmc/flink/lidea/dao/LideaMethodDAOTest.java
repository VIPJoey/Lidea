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
import com.mmc.lidea.common.bo.LideaMethodBO;
import com.mmc.flink.lidea.dto.LideaMethodReq;
import com.mmc.flink.lidea.dto.LideaMethodResp;
import com.mmc.lidea.util.TimeUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Joey
 * @date 2019/8/6 21:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LideaMethodDAOTest {

    @Resource
    private LideaMethodDAO lideaMethodDAO;

    @Test
    public void put() {

        LideaMethodBO bo = new LideaMethodBO();
        bo.setTime(TimeUtil.dateToString(LocalDateTime.now()));
        bo.setAppName("demo");
        bo.setServiceName("com.demo.api.CabinetServiceFacade");
        bo.setMethodName("getCabinetInfo");
        bo.setCount("100");

        bo = lideaMethodDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));
    }

    @Test
    public void scan() {

        LideaMethodReq req = new LideaMethodReq();
        req.setAppName("demo");
        req.setServiceName("com.demo.api.CabinetServiceFacade");

        List<LideaMethodBO> list = lideaMethodDAO.scan(req);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(list));

    }

    @Test
    public void testGet() {

        LideaMethodReq req = new LideaMethodReq();

        req.setAppName("demo");
        req.setServiceName("com.demo.api.CabinetServiceFacade");
        req.setMethodName("getCabinetInfo");


        LideaMethodResp resp = lideaMethodDAO.get(req);
        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(resp));

    }
}