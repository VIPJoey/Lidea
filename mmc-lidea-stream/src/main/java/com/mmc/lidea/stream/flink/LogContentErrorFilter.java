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

import com.mmc.lidea.common.context.Const;
import com.mmc.lidea.stream.model.LogContent;
import com.mmc.lidea.stream.util.LogContentUtil;

/**
 * @author Joey
 * @date 2019/8/4 16:45
 */
public class LogContentErrorFilter implements org.apache.flink.api.common.functions.FilterFunction<String> {

    private static final long serialVersionUID = -8905991913035855962L;

    /**
     * The filter function that evaluates the predicate.
     *
     * <p><strong>IMPORTANT:</strong> The system assumes that the function does not
     * modify the elements on which the predicate is applied. Violating this assumption
     * can lead to incorrect results.
     *
     * @param element The value to be filtered.
     * @return True for values that should be retained, false for values to be filtered out.
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    @Override
    public boolean filter(String element) throws Exception {

        LogContent content = LogContentUtil.valueOf(element);
        if (null == content)
            return false;

        // 只能故障数据通过
        // 必须要有traceId
        return (content.type == Const.ERROR) && (content.traceId != null);
    }
}
