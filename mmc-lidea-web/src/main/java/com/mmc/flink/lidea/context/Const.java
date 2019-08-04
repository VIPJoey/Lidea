/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.flink.lidea.context;

import com.mmc.lidea.util.BytesUtils;

/**
 * @author Joey
 * @date 2019/7/26 19:41
 */
public class Const {

    /**
     * appName + serviceName + method最大长度 md5  = 32.
     */
    public static final int MAX_ROW_KEY_LEN = 32;
    /**
     * 固定8位时间长度.
     */
    public static final int FIX_LONG_LENGTH = 8;
    /**
     * LideaLog表列族.
     */
    public static final byte[] LIDEA_LOG_FEMILY = BytesUtils.toBytes("S");
    /**
     * LideaLog表.
     */
    public static final String LIDEA_LOG_TABLE = "LideaLog";
}
