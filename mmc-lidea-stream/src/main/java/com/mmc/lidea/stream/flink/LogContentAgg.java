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

import com.mmc.lidea.stream.context.Const;
import com.mmc.lidea.stream.model.LogContent;
import org.apache.flink.api.common.functions.AggregateFunction;

/**
 * @author Joey
 * @date 2019/8/1 19:54
 */
public class LogContentAgg implements AggregateFunction<LogContent, LogContentAcc, LogContentAcc> {

    private static final long serialVersionUID = -8449536961395080960L;


    /**
     * Creates a new accumulator, starting a new aggregate.
     *
     * <p>The new accumulator is typically meaningless unless a value is added
     * via {@link #add(Object, Object)}.
     *
     * <p>The accumulator is the state of a running aggregation. When a program has multiple
     * aggregates in progress (such as per key and window), the state (per key and window)
     * is the size of the accumulator.
     *
     * @return A new accumulator, corresponding to an empty aggregate.
     */
    @Override
    public LogContentAcc createAccumulator() {
        return new LogContentAcc();
    }

    /**
     * Adds the given input value to the given accumulator, returning the
     * new accumulator value.
     *
     * <p>For efficiency, the input accumulator may be modified and returned.
     *
     * @param value       The value to add
     * @param accumulator The accumulator to add the value to
     */
    @Override
    public LogContentAcc add(LogContent value, LogContentAcc accumulator) {

        if (null != value) {
            accumulator.access += 1;
            accumulator.cost += value.cost;
            if (value.type == Const.ERROR) {
                accumulator.exception += 1;
                accumulator.traceIds.add(value.traceId);
            }
        }

        return accumulator;
    }

    /**
     * Gets the result of the aggregation from the accumulator.
     *
     * @param accumulator The accumulator of the aggregation
     * @return The final aggregation result.
     */
    @Override
    public LogContentAcc getResult(LogContentAcc accumulator) {

        return accumulator;
    }

    /**
     * Merges two accumulators, returning an accumulator with the merged state.
     *
     * <p>This function may reuse any of the given accumulators as the target for the merge
     * and return that. The assumption is that the given accumulators will not be used any
     * more after having been passed to this function.
     *
     * @param a An accumulator to merge
     * @param b Another accumulator to merge
     * @return The accumulator with the merged state
     */
    @Override
    public LogContentAcc merge(LogContentAcc a, LogContentAcc b) {
        return null;
    }
}
