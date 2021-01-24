package com.study.gmall.ums.api;

import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.MemberEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UmsApi{

    @GetMapping("ums/member/query")
    Resp<MemberEntity> queryUser(@RequestParam("username")String username, @RequestParam("password")String password);
    }
