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
 * @author Joey
 * @date 2019/7/26 17:57
 */
@Data
public class LideaLogErrorDetailBO implements Serializable {

    private static final long serialVersionUID = 6673495081894666234L;

    public String time;
    public String traceId;
    public int type;
    public String localIp;
    public String remoteIp;
    public String appName;
    public String serviceName;
    public String methodName;
    public String args;
    public String response;
    public int cost;
    public String msg;
    public String customMsg;
}
