package com.example.demo;

import com.example.demo.base.User;
import com.example.demo.comment.SessBean;
import com.example.demo.comment.SessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private Student student;

    @PostMapping("/user")
    public ApiResult<User> add(@RequestBody User user){
        SessBean sessBean = new SessBean();
        sessBean.setLocale("CN");
        SessUtil.setSess(sessBean);
        return ApiResult.success(user);
    }

    @RequestMapping("/hello")
    public ApiResult<String> hello() {

        SessBean sessBean = new SessBean();
        sessBean.setLocale("CN");
        SessUtil.setSess(sessBean);

        ApiResult<String> apiResult = ApiResult.success("asdf");

        return apiResult;
    }
}
