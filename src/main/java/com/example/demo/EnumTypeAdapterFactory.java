package com.example.demo;

import com.example.demo.base.Status;
import com.example.demo.comment.SessUtil;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
class EnumTypeAdapterFactory implements TypeAdapterFactory {
    public static final String ERROR_CODE = "ERROR_CODE ";
    public static final String ERROR_CODE_SUCCESS = "ERROR_CODE_SUCCESS";
    public static final String RESULT = "RESULT ";
    public static final String ERROR_MSG = "ERROR_MSG ";
    public static final String SEPARATOR = "&#-SEPARATOR-#&";

    private static Map<String, Map<Enum, String>> cache = new HashMap<>();
    static {
        Map<Enum, String> cacheCN = new HashMap<>();
        cacheCN.put(ApiResultStatus.SUCCESS, "二百");
        cacheCN.put(Status.child, "小孩");
        cacheCN.put(Status.adult, "大人");

        Map<Enum, String> cacheEN = new HashMap<>();
        cacheEN.put(ApiResultStatus.SUCCESS, "two hundred");
        cacheEN.put(Status.adult, "adult");

        cache.put("CN",cacheCN);
        cache.put("EN",cacheEN);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> rawType = (Class<T>) typeToken.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        //https://ejin66.github.io/2018/12/19/gson-enum.html
//        val maps = mutableMapOf<T, ValueType>()


        final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, typeToken);
        final TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                String locale = SessUtil.getSess().getLocale();
                Map<Enum, String> ca = cache.get(locale);
                System.out.println(value);
                out.value(ca.get(value));
//                delegateAdapter.write(out, ca.get(value));
            }

            @Override
            public T read(JsonReader in) throws IOException {
                /*if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String str = in.nextString();
                String locale = SessUtil.getSess().getLocale();
                Map<Enum, String> ca = cache.get(locale);
                for (Enum en : ca.keySet()){
                    if (str.equals(ca.get(en)))
                        return (T) en;
                }
                return null;*/
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String str = in.nextString();
                String locale = SessUtil.getSess().getLocale();
                Map<Enum, String> ca = cache.get(locale);
                for (Enum en : ca.keySet()){
                    if (str.equals(ca.get(en)))
                        return (T) en;
                }
                return null;
            }
        }.nullSafe();
    }
}
