package com.study.gmall.service;

import com.study.gmall.search.pojo.SearchParamVO;

import java.io.IOException;

public interface SearchPmsService {

    void search(SearchParamVO searchParamVO) throws IOException;
}
