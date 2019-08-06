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
import com.mmc.flink.lidea.bo.LideaMethodBO;
import com.mmc.flink.lidea.dto.LideaMethodReq;
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

import static org.junit.Assert.*;

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
        bo.setAppName("cabinet-base-server");
        bo.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        bo.setMethodName("getCabinetInfo");

        bo = lideaMethodDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));
    }

    @Test
    public void scan() {

        LideaMethodReq req = new LideaMethodReq();
        req.setAppName("cabinet-base-server");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");

        List<LideaMethodBO> list = lideaMethodDAO.scan(req);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(list));

    }
}