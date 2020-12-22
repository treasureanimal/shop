package com.study.gmall.service;

import com.study.gmall.search.pojo.SearchParamVO;
import com.study.gmall.search.pojo.SearchResponseVO;

import java.io.IOException;

public interface SearchPmsService {

    SearchResponseVO search(SearchParamVO searchParamVO) throws IOException;
}
