package com.study.gmall.service.impl;

import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.feign.GmallSmsClientApi;
import com.study.gmall.feign.GmallWmsClientApi;
import com.study.gmall.item.ItemVO;
import com.study.gmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemServiceImpl implements ItemService {

    @Autowired
    private GmallSmsClientApi gmallSmsClientApi;
    @Autowired
    private GmallPmsClientApi gmallPmsClientApi;
    @Autowired
    private GmallWmsClientApi gmallWmsClientApi;

    @Override
    public ItemVO queryItemVO(Long skuId) {
        ItemVO itemVO = new ItemVO();

        //设置sku属性
        itemVO.setSkuId(skuId);
        //根据Id查sku
        return null;
    }
}
