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

import com.mmc.flink.lidea.bo.LideaAppBO;
import lombok.Data;

import java.util.List;

/**
 * @author Joey
 * @date 2019/7/26 20:47
 */
@Data
public class LideaAppResp {


    /**
     * 数据.
     */
    private List<LideaAppBO> data;

}
