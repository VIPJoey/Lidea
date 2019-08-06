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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joey
 * @date 2019/8/6 19:47
 */
public class LogAppNameUtil {

    private static Map<String, String> names = new HashMap<>();

    public static boolean exists(String name) {

        if (names.containsKey(name)) {
            return true;
        }
        names.put(name, name);
        return false;
    }

}
