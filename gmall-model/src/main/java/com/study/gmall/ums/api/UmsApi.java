package com.study.gmall.ums.api;

import com.study.core.bean.Resp;
import com.study.gmall.ums.entity.MemberEntity;
import com.study.gmall.ums.entity.MemberReceiveAddressEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UmsApi{

    @GetMapping("ums/member/query")
    Resp<MemberEntity> queryUser(@RequestParam("username")String username, @RequestParam("password")String password);

    @GetMapping("ums/memberreceiveaddress/{userId}")
    Resp<List<MemberReceiveAddressEntity>> queryAddressByUserId(@PathVariable("userId") Long userId);

    @GetMapping("ums/member/info/{id}")
    Resp<MemberEntity> queryMemberById(@PathVariable("id") Long id);
}
