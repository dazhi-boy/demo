package com.example.demo.action;

import com.example.demo.bean.User;
import com.example.demo.comment.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PostMapping("/user")
    public ApiResult<User> add(@RequestBody User user){
        return new ApiResult().success(user);
    }

    @RequestMapping("/hello")
    public ApiResult<String> hello() throws InterruptedException {
//        Thread.sleep(50);
        ApiResult<String> apiResult = new ApiResult().success("asdf");

        return apiResult;
    }
}
