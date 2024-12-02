package com.qin.cwgl.common.utils;

import java.util.HashMap;
//Map 工具类，它继承自 HashMap<String, Object>，主要目的是提供链式调用功能
//Map tool class, inherited from HashMap<String, Object>, provides chain call function
/**
 * Map工具类

 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
