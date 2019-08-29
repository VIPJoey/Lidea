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

import com.mmc.flink.lidea.common.bo.LideaLogErrorDetailBO;
import lombok.Data;

import java.util.List;

/**
 * @author Joey
 * @date 2019/7/26 20:47
 */
@Data
public class LideaLogErrorDetailResp {

    /**
     * 系统名称.
     */
    private String appName;
    /**
     * 接口名称.
     */
    private String serviceName;
    /**
     * 方法名称.
     */
    private String methodName;
    /**
     * 数据.
     */
    private List<LideaLogErrorDetailBO> data;

}
