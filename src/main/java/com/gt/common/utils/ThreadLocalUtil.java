package com.gt.common.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {
    private final static ThreadLocal<Map<String, Object>> threadlocal = new ThreadLocal<>();

    public static void remove() {
        threadlocal.remove();
    }

    public static void set(String key, Object value) {
        getContext().put(key, value);
    }

    public static Object get(String key) {
        return getContext().get(key);
    }

    static private Map<String, Object> getContext() {
        Map map = threadlocal.get();
        if (map == null) {
            map = new HashMap();
            threadlocal.set(map);
        }
        return map;
    }
}
