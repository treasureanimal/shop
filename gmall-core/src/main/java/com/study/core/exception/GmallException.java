package com.study.core.exception;

import com.study.core.reponse.ResultCode;

public class GmallException extends RuntimeException {
    private final ResultCode resultCode;

    public GmallException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return this.resultCode;
    }
}
