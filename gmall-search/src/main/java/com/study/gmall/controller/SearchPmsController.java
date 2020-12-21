package com.study.gmall.controller;

import com.study.core.bean.Resp;
import com.study.gmall.search.pojo.SearchParamVO;
import com.study.gmall.service.SearchPmsService;
import org.elasticsearch.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchPmsController {

    @Autowired
    public SearchPmsService searchPmsService;

    @GetMapping
    public Resp<Object> search(SearchParamVO searchParamVO){

        this.searchPmsService.search(searchParamVO);
        return Resp.ok(null);
    }
}
