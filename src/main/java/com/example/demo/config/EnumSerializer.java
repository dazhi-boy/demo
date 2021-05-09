package com.example.demo.config;

import com.example.demo.comment.CacheEnum;
import com.google.gson.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Map;

@Component
public class EnumSerializer<T extends Enum<T>> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Resource
    private CacheEnum cacheEnum;

    // 对象转为Json时调用,实现JsonSerializer<PackageState>接口
    @Override
    public JsonElement serialize(T state, Type arg1, JsonSerializationContext arg2) {
        String locale = LocaleContextHolder.getLocale().toString();
        Map<Enum, String> ca = cacheEnum.getCache(locale);
        return new JsonPrimitive(ca.get(state));
    }

    // json转为对象时调用,实现JsonDeserializer<PackageState>接口
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        System.out.println(typeOfT.getTypeName());
        String locale = LocaleContextHolder.getLocale().toString();
        Map<Enum, String> ca = cacheEnum.getCache(locale);
        for (Enum en : ca.keySet()){
            if (json.getAsString().equals(ca.get(en)))
                return (T) en;
        }
        return null;
    }
}
