/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */

package com.mmc.lidea.common.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用名称.
 * @author Joey
 * @date 2019/8/6 18:36
 */
@Data
public class LideaAppBO implements Serializable {

    private static final long serialVersionUID = -2296577378425040440L;

    public String appName;
    public String time;

}
