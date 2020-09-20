package com.study.core.reponse;

public interface ResultCode {
    boolean success();
    int code();
    String message();
}
