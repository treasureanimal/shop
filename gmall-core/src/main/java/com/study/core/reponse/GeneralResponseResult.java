package com.study.core.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponseResult<T> extends ResponseResult {

    private T date;
    public GeneralResponseResult(ResultCode resultCode) {
        super(resultCode);
    }
    public GeneralResponseResult(ResultCode resultCode, T date) {
        super(resultCode);
        this.date = date;
    }

    public static GeneralResponseResult SUCCESS(){return new GeneralResponseResult(CommonCode.SUCCESS);}
    public static GeneralResponseResult FAIL(){return new GeneralResponseResult(CommonCode.FAIL);}
}
