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
import com.mmc.flink.lidea.dto.LideaMethodReq;
import com.mmc.flink.lidea.dto.LideaServiceReq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Joey
 * @date 2019/7/28 15:01
 */
@Controller
@RequestMapping("/lidea")
public class LideaController {

    @RequestMapping({"", "ServerTest", "ServerTest.*", "index", "index.*"})
    public String hello() {
        return app();
    }

    @RequestMapping("/app")
    public String app() {

        return "/pages/v1/app.html";
    }

    @RequestMapping("/serviceList")
    public String serviceList(LideaServiceReq req, Model model) {

        model.addAttribute("appName", req.getAppName());

        return "/pages/v1/serviceList.html";
    }

    @RequestMapping("/methodList")
    public String methodList(LideaMethodReq req, Model model) {

        model.addAttribute("appName", req.getAppName());
        model.addAttribute("serviceName", req.getServiceName());

        return "/pages/v1/methodList.html";
    }

    @RequestMapping("/detail")
    public String detail(LideaLogReq req, RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("appName", req.getAppName());
        redirectAttributes.addAttribute("serviceName", req.getServiceName());
        redirectAttributes.addAttribute("methodName", req.getMethodName());

        return "redirect:/lidea/redetail";
    }

    @RequestMapping("/redetail")
    public String reDetail(LideaLogReq req, Model model) {

        model.addAttribute("appName", req.getAppName());
        model.addAttribute("serviceName", req.getServiceName());
        model.addAttribute("methodName", req.getMethodName());

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
