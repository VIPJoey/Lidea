/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Joey
 * @date 2019/7/26 17:57
 */
@Data
public class LideaLogBO implements Serializable {

    private static final long serialVersionUID = 6673495081894666234L;

    /**
     * 记录时间.
     */
    private String ltime;
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
    /**
     * 平均响应时间(ms).
     */
    private int lavg;
    /**
     * 故障次数.
     */
    private int lexception;
}
