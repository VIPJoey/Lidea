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
 * @date 2019/8/6 20:41
 */
@Data
public class LideaMethodReq {

    /**
     * 系统名称.
     */
    private String appName;
    /**
     * 接口名称.
     */
    private String serviceName;


}
