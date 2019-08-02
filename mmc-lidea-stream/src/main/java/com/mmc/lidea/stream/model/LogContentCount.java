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

    public long count;
    public int avg;

    @Override
    public String toString() {
        return StringUtil.format("{}, {}, {}, {}, {}, {}", time, appName, serviceName, methodName, count, avg);
    }
}
