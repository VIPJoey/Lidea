/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.common.bo;

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
    public String time;
    /**
     * 系统名称.
     */
    public String appName;
    /**
     * 接口名称.
     */
    public String serviceName;
    /**
     * 方法名称.
     */
    public String methodName;
    /**
     * 访问量.
     */
    public long count;
    /**
     * 平均响应时间(ms).
     */
    public int avg;
    /**
     * 故障次数.
     */
    public int exception;
    /**
     * 故障ID.
     */
    public String traceIds;
}
