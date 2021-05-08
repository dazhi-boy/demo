package com.example.demo;

class ApiResult<T> {

    private ApiResultStatus status;
    private String message;
    private T data;


    public ApiResult(ApiResultStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ApiResultStatus.SUCCESS, "success", data);
    }

}
