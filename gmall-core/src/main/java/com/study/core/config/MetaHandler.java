package com.study.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;

public class MetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("makeDate",new Date(),metaObject);
        this.setFieldValByName("makeTime",getTime(),metaObject);
        this.setFieldValByName("modifyDate",new Date(),metaObject);
        this.setFieldValByName("modifyTime",getTime(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifyDate",new Date(),metaObject);
        this.setFieldValByName("modifyTime",getTime(),metaObject);
    }

    private String getTime(){
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        int second = LocalDateTime.now().getSecond();
        return  hour+":"+minute+":"+second;
    }
}
