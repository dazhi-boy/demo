package com.example.demo;

import com.example.demo.base.MyHandlerInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private EnumSerializer enumSerializer;
    @Resource
    private EnumTypeAdapterFactory enumTypeAdapterFactory;
    @Resource
    private MyHandlerInterceptor myHandlerInterceptor;
    @Bean
    GsonHttpMessageConverter gsonHttpMessageConverter() {
        GsonBuilder builder = new GsonBuilder();

//        builder.registerTypeAdapter(ApiResultStatus.class, enumSerializer);
        builder.registerTypeAdapterFactory(enumTypeAdapterFactory);
        Gson gson = builder.create();

        return new GsonHttpMessageConverter(gson);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
    }
}
