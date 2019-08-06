/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.controller;

import com.mmc.flink.lidea.dao.LideaLogDAO;
import com.mmc.flink.lidea.dto.LideaLogErrorDetailResp;
import com.mmc.flink.lidea.dto.LideaLogReq;
import com.mmc.flink.lidea.dto.LideaLogResp;
import com.mmc.flink.lidea.dto.ResultDTO;
import com.mmc.flink.lidea.service.LideaLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Joey
 * @date 2019/7/28 16:11
 */
@RestController
@RequestMapping("/lidea/detail/")
public class DetailController {

    @Resource
    private LideaLogDAO lideaLogDAO;

    @Resource
    private LideaLogService lidiaLogService;


    @RequestMapping("/access")
    public ResultDTO<LideaLogResp> getAccess(LideaLogReq req) {

        if (null == req) {
            throw new RuntimeException("req can't be null.");
        }

        LideaLogResp data = lideaLogDAO.scan(req);


        return ResultDTO.handleSuccess("SUCESS", data);
    }

    @RequestMapping("/error")
    public ResultDTO<LideaLogErrorDetailResp> getError(LideaLogReq req) {

        if (null == req) {
            return ResultDTO.handleError("req can't be null.", null);
        }

        LideaLogErrorDetailResp data = lidiaLogService.listError(req);

        return ResultDTO.handleSuccess("SUCESS", data);
    }
}
