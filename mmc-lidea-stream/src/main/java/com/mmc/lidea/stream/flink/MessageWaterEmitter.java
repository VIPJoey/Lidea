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

import com.mmc.lidea.util.TimeUtil;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * @author Joey
 * @date 2019/7/14 15:00
 */
public class MessageWaterEmitter implements AssignerWithPunctuatedWatermarks<String> {

    private static final long serialVersionUID = -6218113758475504882L;

    /**
     * Asks this implementation if it wants to emit a watermark. This method is called right after
     * the {@link #extractTimestamp(Object, long)} method.
     *
     * <p>The returned watermark will be emitted only if it is non-null and its timestamp
     * is larger than that of the previously emitted watermark (to preserve the contract of
     * ascending watermarks). If a null value is returned, or the timestamp of the returned
     * watermark is smaller than that of the last emitted one, then no new watermark will
     * be generated.
     *
     * <p>For an example how to use this method, see the documentation of
     * {@link AssignerWithPunctuatedWatermarks this class}.
     *
     * @param lastElement
     * @param extractedTimestamp
     * @return {@code Null}, if no watermark should be emitted, or the next watermark to emit.
     */
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(String lastElement, long extractedTimestamp) {

        if (null != lastElement && lastElement.contains("|")) {

            String[] parts = lastElement.split("\\|");
//            long timestamp = Long.parseLong(parts[0]);
            long timestamp = TimeUtil.stringToLong(parts[0], TimeUtil.yyyyMMddHHmmssSSS);
            return new Watermark(timestamp);
        }
        return new Watermark(extractedTimestamp);
    }

    /**
     * Assigns a timestamp to an element, in milliseconds since the Epoch.
     *
     * <p>The method is passed the previously assigned timestamp of the element.
     * That previous timestamp may have been assigned from a previous assigner,
     * by ingestion time. If the element did not carry a timestamp before, this value is
     * {@code Long.MIN_VALUE}.
     *
     * @param element                  The element that the timestamp will be assigned to.
     * @param previousElementTimestamp The previous internal timestamp of the element,
     *                                 or a negative value, if no timestamp has been assigned yet.
     * @return The new timestamp.
     */
    @Override
    public long extractTimestamp(String element, long previousElementTimestamp) {
        if (element != null && element.contains("|")) {
            String[] parts = element.split("\\|");
//            long timestamp = Long.parseLong(parts[0]);
            long timestamp = TimeUtil.stringToLong(parts[0], TimeUtil.yyyyMMddHHmmssSSS);

            return timestamp;
        }
        return previousElementTimestamp;
    }
}
