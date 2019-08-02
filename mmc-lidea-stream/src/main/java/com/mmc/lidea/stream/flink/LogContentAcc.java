/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.stream.flink;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joey
 * @date 2019/8/2 14:00
 */
public class LogContentAcc {

    public int access;
    public int exception;
    public int cost;
    public List<String> traceIds = new ArrayList<>();
}
