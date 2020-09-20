package com.study.core.exception;

import com.study.core.reponse.ResponseResult;
import com.study.core.reponse.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionCatch {
    @ExceptionHandler(GmallException.class)
    @ResponseBody
    public ResponseResult GmallExceptionHandler(GmallException gmallException) {
        ResultCode resultCode = gmallException.getResultCode();
        return new ResponseResult(resultCode);

    }
}
