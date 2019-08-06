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
import com.mmc.flink.lidea.bo.LideaAppBO;
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
 * @date 2019/8/6 18:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LideaAppDAOTest {


    @Resource
    private LideaAppDAO lideaAppDAO;

    @Test
    public void testPut() {

        LideaAppBO bo = new LideaAppBO();
        bo.appName = "cabinet-conf-server";
        bo.time = TimeUtil.dateToString(LocalDateTime.now());

        bo = lideaAppDAO.put(bo);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(bo));

    }

    @Test
    public void testScan() {


        List<LideaAppBO> list = lideaAppDAO.scan();

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(list));
    }

}