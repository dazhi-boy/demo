package com.example.demo.config;

import com.example.demo.comment.CacheEnum;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Component
public class EnumTypeAdapterFactory implements TypeAdapterFactory {
    @Resource
    private CacheEnum cacheEnum;

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> rawType = (Class<T>) typeToken.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        //https://ejin66.github.io/2018/12/19/gson-enum.html

        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                String locale = LocaleContextHolder.getLocale().toString();
                Map<Enum, String> ca = cacheEnum.getCache(locale);
                out.value(ca.get(value));
            }

            @Override
            public T read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String str = in.nextString();
                String locale = LocaleContextHolder.getLocale().toString();
                Map<Enum, String> ca = cacheEnum.getCache(locale);
                for (Enum en : ca.keySet()){
                    if (str.equals(ca.get(en)))
                        return (T) en;
                }
                return null;
            }
        }.nullSafe();
    }
}
