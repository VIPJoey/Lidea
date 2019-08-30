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
import com.mmc.lidea.common.bo.LideaServiceBO;
import com.mmc.flink.lidea.dto.LideaServiceReq;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author Joey
 * @date 2019/8/29 14:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LideaServiceDAOTest {

    @Resource
    private LideaServiceDAO lideaServiceDAO;

    @Test
    public void testPut() {

        LideaServiceBO bo = new LideaServiceBO();
        bo.appName = "cabinet-base-server";
        bo.serviceName = "com.fcbox.edms.terminal.api.CabinetServiceFacade";

        bo = lideaServiceDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));

    }

    @Test
    public void scan() {

        LideaServiceReq req = new LideaServiceReq();
        req.setAppName("cabinet-base-server");

        List<LideaServiceBO> list = lideaServiceDAO.scan(req);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(list));

    }



}