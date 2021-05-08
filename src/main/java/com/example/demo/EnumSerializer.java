package com.example.demo;

import com.example.demo.comment.SessUtil;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
class EnumSerializer<T extends Enum<T>> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static Map<String, Map<Enum, String>> cache = new HashMap<>();
    static {
        Map<Enum, String> cacheCN = new HashMap<>();
        cacheCN.put(ApiResultStatus.SUCCESS, "二百");

        Map<Enum, String> cacheEN = new HashMap<>();
        cacheEN.put(ApiResultStatus.SUCCESS, "two hundred");

        cache.put("CN",cacheCN);
        cache.put("EN",cacheEN);
    }
    // 对象转为Json时调用,实现JsonSerializer<PackageState>接口
    @Override
    public JsonElement serialize(T state, Type arg1, JsonSerializationContext arg2) {
        String locale = SessUtil.getSess().getLocale();
//		return new JsonPrimitive(state.status);
        Map<Enum, String> ca = cache.get(locale);
        return new JsonPrimitive(ca.get(state));
    }

    // json转为对象时调用,实现JsonDeserializer<PackageState>接口
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        System.out.println(typeOfT.getTypeName());
		/*cache.forEach((key, value) -> {
			if (value.equals(json.getAsString())) return key;
		});*/
        String locale = SessUtil.getSess().getLocale();
        Map<Enum, String> ca = cache.get(locale);
        for (Enum en : ca.keySet()){
            if (json.getAsString().equals(ca.get(en)))
                return (T) en;
        }
        return null;
    }
}
