package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.ums.entity.UserLoginLogEntity;


/**
 * 用户登陆记录表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
public interface UserLoginLogService extends IService<UserLoginLogEntity> {

    PageVo queryPage(QueryCondition params);
}

