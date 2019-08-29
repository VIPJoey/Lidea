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
 * 接口名称.
 * @author Joey
 * @date 2019/8/29 14:26
 */
@Data
public class LideaServiceBO implements Serializable {

    private static final long serialVersionUID = -4111289278389251339L;

    public String appName;
    public String serviceName;

}
