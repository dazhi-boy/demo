package com.example.demo.base;

import com.example.demo.comment.SessBean;
import com.example.demo.comment.SessUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessBean sessBean = new SessBean();
        sessBean.setLocale("CN");
        SessUtil.setSess(sessBean);
        return true;
    }
}
