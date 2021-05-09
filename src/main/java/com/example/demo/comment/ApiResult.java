package com.example.demo.comment;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApiResult<T> {

    private ApiResultStatus status;
    private String message;
    private T data;

    public ApiResult(){}

    public ApiResult(ApiResultStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ApiResultStatus.SUCCESS, MessageUtils.get("api.success"), data);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}

enum ApiResultStatus {
    SUCCESS,FAILED,UNDEFINED;
}
