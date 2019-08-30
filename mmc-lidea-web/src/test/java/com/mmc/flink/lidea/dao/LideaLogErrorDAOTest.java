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
import com.mmc.lidea.common.bo.LideaLogErrorDetailBO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joey
 * @date 2019/8/5 16:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LideaLogErrorDAOTest {

    @Resource
    private
    LideaLogErrorDAO lideaLogErrorDAO;

    @Test
    public void list() {

        List<String> traceIds = new ArrayList<>();
        traceIds.add("a3f2f2660d6c4a869626c878be92961d");

        List<LideaLogErrorDetailBO> ret = lideaLogErrorDAO.list(traceIds);


        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(ret));

    }
}