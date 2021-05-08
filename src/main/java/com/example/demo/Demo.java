package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class Demo {


    public static void main(String[] args) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ApiResultStatus.class, new EnumSerializer());
        Gson gson = gsonBuilder.create();

        ApiResult<String> apiResult = ApiResult.success("asdf");
        System.out.println(gson.toJson(apiResult));

        System.out.println(gson.fromJson("200", ApiResultStatus.class));
        System.out.println(gson.fromJson("300", ApiResultStatus.class));
    }

}
