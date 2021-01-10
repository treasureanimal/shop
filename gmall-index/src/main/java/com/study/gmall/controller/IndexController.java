package com.study.gmall.controller;

import com.study.core.bean.Resp;
import com.study.gmall.pms.entity.CategoryEntity;
import com.study.gmall.pms.vo.CategoryVO;
import com.study.gmall.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("index")
public class IndexController {


    @Autowired
    private IndexService indexService;
    /**
     * 查询所有1级分类
     * @return Resp.ok(categoryEntities)
     */
    @GetMapping("cates")
    public Resp<List<CategoryEntity>> queryLV1CateGories(){
        List<CategoryEntity> categoryEntities = indexService.queryLV1CateGories();
        return Resp.ok(categoryEntities);
    }

    @GetMapping("cates/{pid}")
    public Resp<List<CategoryVO>> querySubCategories(@PathVariable("pid") Long pid){
       List<CategoryVO> categoryVOS = indexService.querySubCategories(pid);
       return Resp.ok(categoryVOS);
    }
}
