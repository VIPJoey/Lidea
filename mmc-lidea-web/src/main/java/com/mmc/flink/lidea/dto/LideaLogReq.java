/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.dto;

import lombok.Data;

/**
 * @author Joey
 * @date 2019/7/26 20:46
 */
@Data
public class LideaLogReq {

    private long from;
    private long to;
    private int size;


    /**
     * 系统名称.
     */
    private String lapp;
    /**
     * 接口名称.
     */
    private String linterface;
    /**
     * 方法名称.
     */
    private String lmethod;
    /**
     * 访问量.
     */
    private int lcount;


}
