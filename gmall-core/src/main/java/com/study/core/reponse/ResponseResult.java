package com.study.core.reponse;

public class ResponseResult {
    /**
     * 操作成功
     */
    protected boolean success;
    /**
     * 状态码
     */
    protected int code;
    /**提示信息
     *
     */
    protected String message;

    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static ResponseResult SUCCESS(){return new ResponseResult(CommonCode.SUCCESS);}
    public static ResponseResult FAIL(){return new ResponseResult(CommonCode.FAIL);}
    public static ResponseResult FAIL(ResultCode resultCode){ return  new ResponseResult(resultCode);}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
