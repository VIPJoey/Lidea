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

import com.mmc.lidea.stream.model.LogContentCount;
import com.mmc.lidea.util.TimeUtil;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author Joey
 * @date 2019/8/1 19:54
 */
public class LogContentWinFun implements WindowFunction<Long, LogContentCount, Tuple, TimeWindow> {

    private static final long serialVersionUID = 2539721015512513020L;


    /**
     * Evaluates the window and outputs none or several elements.
     *
     * @param orgin  The key for which this window is evaluated.
     * @param window The window that is being evaluated.
     * @param input  The elements in the window being evaluated.
     * @param out    A collector for emitting elements.
     * @throws Exception The function may throw exceptions to fail the program and trigger recovery.
     */
    @Override
    public void apply(Tuple orgin, TimeWindow window, Iterable<Long> input, Collector<LogContentCount> out) throws Exception {

        Tuple3<String, String, String> tuple = (Tuple3<String, String, String>) orgin;
        Long count = input.iterator().next();

        long begin = window.getEnd();
        String time = TimeUtil.timestampToString(begin);

        LogContentCount result = new LogContentCount();
        result.time = time;
        result.appName = tuple.f0; // 系统
        result.serviceName = tuple.f1; // 接口
        result.methodName = tuple.f2; // 方法
        result.count = count; // 调用次数
        result.avg = 0; // 平均响应时间
        out.collect(result);

    }
}
