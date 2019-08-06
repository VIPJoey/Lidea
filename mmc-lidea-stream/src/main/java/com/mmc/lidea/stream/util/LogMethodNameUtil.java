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
 * @date 2019/8/6 22:07
 */
public class LogMethodNameUtil {

    private static Map<String, String> methods = new HashMap<>();

    public static boolean exist(String pwd) {

        return methods.containsKey(pwd);
    }

    public static void put(String pwd) {
        methods.put(pwd, "1");
    }
}
