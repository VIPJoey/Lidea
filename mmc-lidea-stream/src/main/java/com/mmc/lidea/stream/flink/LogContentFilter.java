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

/**
 * @author Joey
 * @date 2019/8/1 19:44
 */
public class LogContentFilter implements org.apache.flink.api.common.functions.FilterFunction<String> {

    private static final long serialVersionUID = -1475916466164561184L;

    /**
     * The filter function that evaluates the predicate.
     *
     * <p><strong>IMPORTANT:</strong> The system assumes that the function does not
     * modify the elements on which the predicate is applied. Violating this assumption
     * can lead to incorrect results.
     *
     * @param value The value to be filtered.
     * @return True for values that should be retained, false for values to be filtered out.
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    @Override
    public boolean filter(String value) throws Exception {
        return true;
    }
}
