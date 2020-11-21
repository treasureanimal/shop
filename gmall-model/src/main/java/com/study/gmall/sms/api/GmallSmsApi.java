package com.study.gmall.sms.api;

import com.study.core.bean.Resp;
import com.study.gmall.sms.vo.SaleVO;
import com.study.gmall.sms.vo.SkuSaleVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GmallSmsApi {

    @PostMapping("sms/skubounds/sku/sale/save")
    Resp<Object> saveSale(@RequestBody SkuSaleVO skuSaleVO);

    @GetMapping("sms/skubounds/{skuId}")
    Resp<List<SaleVO>> querySalesBySkuId(@PathVariable("skuId")Long skuId);
}
