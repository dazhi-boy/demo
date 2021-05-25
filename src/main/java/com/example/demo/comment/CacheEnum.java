package com.example.demo.comment;

import com.example.demo.base.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CacheEnum {
    private static Map<String, Map<Enum, String>> cache = new HashMap<>();
    static {
        Map<Enum, String> cacheCN = new HashMap<>();
        cacheCN.put(ApiResultStatus.SUCCESS, "二百");
        cacheCN.put(Status.child, "小孩");
        cacheCN.put(Status.adult, "大人");

        Map<Enum, String> cacheEN = new HashMap<>();
        cacheEN.put(ApiResultStatus.SUCCESS, "two hundred");
        cacheEN.put(Status.adult, "adult");

        cache.put("cn_ch",cacheCN);
        cache.put("en_us",cacheEN);
    }

    public static Map<Enum, String> getCache(String locale) {
        return cache.get(locale);
    }
}
