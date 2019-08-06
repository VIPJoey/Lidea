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

import com.alibaba.fastjson.JSON;
import com.mmc.flink.lidea.dto.LideaLogReq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joey
 * @date 2019/7/28 15:01
 */
@Controller
@RequestMapping("/lidea")
public class LideaController {

    @RequestMapping("/detail")
    public String detail() {


        return "/pages/v1/detail.html";
    }

    @RequestMapping("/errorList")
    public String errorList(LideaLogReq req, Model model) {

        model.addAttribute("from", req.getFrom());
        model.addAttribute("to", req.getTo());
        model.addAttribute("size", req.getSize());
        model.addAttribute("req", JSON.toJSONString(req));

        return "pages/v1/errorList.html";
    }
}
