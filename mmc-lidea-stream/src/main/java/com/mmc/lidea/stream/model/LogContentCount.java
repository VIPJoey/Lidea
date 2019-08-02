/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.stream.model;

import com.mmc.lidea.util.StringUtil;

/**
 * @author Joey
 * @date 2019/8/1 19:59
 */
public class LogContentCount {

    public String time;

    public String appName;
    public String serviceName;
    public String methodName;

    /**
     * 调用次数.
     */
    public long count;
    /**
     * 平均响应时间.
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

    @Override
    public String toString() {
        return StringUtil.format("{}, {}, {}, {}, {}, {}, {}, {}",
                time, appName, serviceName, methodName, count, avg, exception, traceIds);
    }
}
