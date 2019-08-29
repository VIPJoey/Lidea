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

import com.mmc.flink.lidea.common.bo.LideaServiceBO;
import lombok.Data;

import java.util.List;

/**
 * @author Joey
 * @date 2019/8/29 15:27
 */
@Data
public class LideaServiceResp {

    /**
     * 系统名称.
     */
    private String appName;

    /**
     * 数据.
     */
    private List<LideaServiceBO> data;
}
