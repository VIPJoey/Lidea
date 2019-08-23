/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.stream.util;

import com.mmc.lidea.stream.model.LogContent;

/**
 * @author Joey
 * @date 2019/8/4 16:49
 */
public class LogContentUtil {

    public static LogContent valueOf(String element) {
        if (element != null && element.contains("|")) {

            try {


                String[] parts = element.split("\\|");

                if (parts.length < 13) {
                    System.out.println(Thread.currentThread().getName() + ", bad message, length < 13");
                    return null;
                }

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

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
