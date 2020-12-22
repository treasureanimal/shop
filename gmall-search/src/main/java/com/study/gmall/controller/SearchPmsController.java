package com.study.gmall.controller;

import com.study.core.bean.Resp;
import com.study.gmall.search.pojo.SearchParamVO;
import com.study.gmall.search.pojo.SearchResponseVO;
import com.study.gmall.service.SearchPmsService;
import org.elasticsearch.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchPmsController {

    @Autowired
    public SearchPmsService searchPmsService;

    @GetMapping
    public Resp<SearchResponseVO> search(SearchParamVO searchParamVO) throws IOException {

        SearchResponseVO search = this.searchPmsService.search(searchParamVO);
        return Resp.ok(search);
    }
}
