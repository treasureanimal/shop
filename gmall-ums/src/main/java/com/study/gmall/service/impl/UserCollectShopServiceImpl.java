package com.study.gmall.service.impl;

import com.study.gmall.dao.UserCollectShopDao;
import com.study.gmall.ums.entity.UserCollectShopEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;

import com.study.gmall.service.UserCollectShopService;


@Service("userCollectShopService")
public class UserCollectShopServiceImpl extends ServiceImpl<UserCollectShopDao, UserCollectShopEntity> implements UserCollectShopService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<UserCollectShopEntity> page = this.page(
                new Query<UserCollectShopEntity>().getPage(params),
                new QueryWrapper<UserCollectShopEntity>()
        );

        return new PageVo(page);
    }

}