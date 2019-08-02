/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.test;

import com.mmc.flink.lidea.bo.LideaLogBO;
import com.mmc.flink.lidea.dto.LideaLogReq;
import com.mmc.flink.lidea.dto.LideaLogResp;
import com.mmc.flink.lidea.service.LideaLogDAO;
import com.mmc.flink.lidea.util.RandomUtil;
import com.mmc.flink.lidea.util.TimeUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        bo.setLtime(TimeUtil.dateToString(LocalDateTime.now()));
        bo.setLapp("cabinet-base-server");
        bo.setLinterface("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        bo.setLmethod("getCabinetInfo");
        bo.setLcount(RandomUtil.getRandomNumberInRange(1, 100));
        bo.setLavg(RandomUtil.getRandomNumberInRange(100, 999));
        bo.setLexception(RandomUtil.getRandomNumberInRange(1, 100));

        lideaLogDAO.put(bo);

    }

    @Test
    public void testPut1() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            testPut();
            Thread.sleep(RandomUtil.getRandomNumberInRange(1, 10) * 1000);
        }

    }

    @Test
    public void testScan() {

        LideaLogReq req = new LideaLogReq();

        req.setLapp("cabinet-base-server");
        req.setLinterface("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        req.setLmethod("getCabinetInfo");

        req.setFrom(TimeUtil.convertTimeToLong("2019-07-20 00:00:00"));
        req.setTo(TimeUtil.convertTimeToLong("2019-07-29 23:00:00"));

        LideaLogResp range = lideaLogDAO.scan(req);
        System.out.println("==========================================================================================");
        System.out.println(range.getData());
    }

}
