/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.service;

import com.mmc.flink.lidea.bo.LideaAppBO;
import com.mmc.flink.lidea.bo.LideaLogErrorDetailBO;
import com.mmc.flink.lidea.bo.LideaMethodBO;
import com.mmc.flink.lidea.dao.LideaAppDAO;
import com.mmc.flink.lidea.dao.LideaLogDAO;
import com.mmc.flink.lidea.dao.LideaLogErrorDAO;
import com.mmc.flink.lidea.dao.LideaMethodDAO;
import com.mmc.flink.lidea.dto.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joey
 * @date 2019/8/5 14:30
 */
@Service("lideaLogService")
public class LideaLogService {

    @Resource
    private LideaLogDAO lideaLogDAO;

    @Resource
    private LideaLogErrorDAO lideaLogErrorDAO;

    @Resource
    private LideaAppDAO lideaAppDAO;

    @Resource
    private LideaMethodDAO lideaMethodDAO;

    public LideaLogErrorDetailResp listError(LideaLogReq req) {

        // 获取traceId列表
        LideaLogResp traceResp = null;
        if (req.getFrom() == req.getTo()) {

            traceResp = lideaLogDAO.get(req);

        } else {

            traceResp = lideaLogDAO.scan(req);
        }

        // 获取trace详情
        List<String> traceIds = traceResp.getData().stream().flatMap(m -> Arrays.stream(m.getTraceIds().split(","))).collect(Collectors.toList());

        List<LideaLogErrorDetailBO> datas = lideaLogErrorDAO.list(traceIds);

        LideaLogErrorDetailResp resp = new LideaLogErrorDetailResp();
        resp.setAppName(req.getAppName());
        resp.setServiceName(req.getServiceName());
        resp.setMethodName(req.getMethodName());

        resp.setData(datas);

        return resp;
    }

    public LideaAppResp listApps() {

        LideaAppResp resp = new LideaAppResp();
        List<LideaAppBO> data = lideaAppDAO.scan();

        resp.setData(data);
        return resp;
    }

    public LideaMethodResp listMethods(LideaMethodReq req) {

        LideaMethodResp resp = new LideaMethodResp();

        List<LideaMethodBO> data = lideaMethodDAO.scan(req);

        resp.setAppName(req.getAppName());
        resp.setServiceName(req.getServiceName());
        resp.setData(data);

        return resp;
    }
}
