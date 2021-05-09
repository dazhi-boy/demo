package com.example.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Resource;
import java.util.Locale;


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

    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("cn_CH"));
        localeResolver.setTimeZoneAttributeName("GMT+8");
        return localeResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 国际化处理
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);

        registry.addInterceptor(myHandlerInterceptor).addPathPatterns("/**");
    }
}
