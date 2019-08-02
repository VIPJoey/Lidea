/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.mmc.lidea.util;

import java.util.Random;

/**
 * @author Joey
 * @date 2019/7/28 17:01
 */
public class RandomUtil {

    /**
     * 任意范围随机数.
     *
     * @param min 起始值
     * @param max 结束值
     * @return 随机值
     */
    public static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();

    }
}
