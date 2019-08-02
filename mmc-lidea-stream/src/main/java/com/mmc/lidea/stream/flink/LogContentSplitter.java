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

import com.mmc.lidea.stream.model.LogContent;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author Joey
 * @date 2019/8/1 19:31
 */
public class LogContentSplitter implements MapFunction<String, LogContent> {

    private static final long serialVersionUID = -5055197964573569120L;


    /**
     * The mapping method. Takes an element from the input data set and transforms
     * it into exactly one element.
     *
     * @param element The input value.
     * @return The transformed value
     * @throws Exception This method may throw exceptions. Throwing an exception will cause the operation
     *                   to fail and may trigger recovery.
     */
    @Override
    public LogContent map(String element) throws Exception {

        if (element != null && element.contains("|")) {
            String[] parts = element.split("\\|");

            int i = 0;
            LogContent content = new LogContent();
            content.time = parts[i++];
            content.traceId = parts[i++];
            content.type = Integer.valueOf(parts[i++]);
            content.localIp = parts[i++];
            content.remoteIp = parts[i++];
            content.appName = parts[i++];
            content.serviceName = parts[i++];
            content.methodName = parts[i++];
            content.args = parts[i++];
            content.response = parts[i++];
            content.cost = Integer.valueOf(parts[i++]);
            content.msg = parts[i++];
            content.customMsg = parts[i++];
            return content;
        }
        return null;
    }
}
