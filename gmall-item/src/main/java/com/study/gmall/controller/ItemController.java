package com.study.gmall.controller;

import com.study.core.bean.Resp;
import com.study.gmall.item.ItemVO;
import com.study.gmall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/skuId")
    public Resp<ItemVO> queryItemVO(@RequestParam Long skuId){
        ItemVO itemVO = itemService.queryItemVO(skuId);
       return Resp.ok(itemVO);
    }
}
