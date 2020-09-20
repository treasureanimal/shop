package com.study.core.reponse;

public enum CommonCode implements ResultCode {
    SUCCESS(true,10000,"操作成功"),
    FAIL(false,11111,"操作失败"),
    UNAUTHENTICATED(false,10001,"请登录系统"),
    UNAUTHORISE(false,10002,"对不起权限不足"),
    ERRPR(false,99999,"对不起，系统繁忙，请稍后重试"),
    PARAM_ERROR(false,10003,"参数校验有误");

    boolean success;
    int code;
    String message;

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return this.success;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "CommonCode{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
