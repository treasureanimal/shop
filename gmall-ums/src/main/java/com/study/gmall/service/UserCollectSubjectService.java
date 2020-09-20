package com.study.gmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.gmall.ums.entity.UserCollectSubjectEntity;


/**
 * 关注活动表
 *
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
public interface UserCollectSubjectService extends IService<UserCollectSubjectEntity> {

    PageVo queryPage(QueryCondition params);
}

