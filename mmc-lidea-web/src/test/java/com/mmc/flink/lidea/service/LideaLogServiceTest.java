package com.mmc.flink.lidea.service;

import com.alibaba.fastjson.JSON;
import com.mmc.flink.lidea.dto.LideaLogErrorDetailResp;
import com.mmc.flink.lidea.dto.LideaLogReq;
import com.mmc.lidea.util.TimeUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LideaLogServiceTest {

    @Resource
    private LideaLogService lideaLogService;

    @Test
    public void testList() {

        LideaLogReq req = new LideaLogReq();
        req.setAppName("cabinet-base-server");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        req.setMethodName("getCabinetInfo");
        req.setFrom(TimeUtil.stringToLong("2019-08-06 10:09:30"));
        req.setTo(TimeUtil.stringToLong("2019-08-06 10:09:30"));
        req.setSize(100);

        LideaLogErrorDetailResp resp = lideaLogService.listError(req);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(resp));
    }
    @Test
    public void testList1() {

        LideaLogReq req = new LideaLogReq();
        req.setAppName("cabinet-base-server");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacade");
        req.setServiceName("com.fcbox.edms.terminal.api.CabinetServiceFacad");
        req.setMethodName("getCabinetInfo");
        req.setFrom(1565057370000L);
        req.setTo(1565057370000L);
        req.setSize(100);

        LideaLogErrorDetailResp resp = lideaLogService.listError(req);

        System.out.println("==========================================================================================");
        System.out.println(JSON.toJSONString(resp));
    }

}
