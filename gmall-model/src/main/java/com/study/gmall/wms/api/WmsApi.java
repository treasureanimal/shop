package com.study.gmall.wms.api;

import com.study.core.bean.Resp;
import com.study.gmall.wms.entity.WareSkuEntity;
import com.study.gmall.wms.vo.SkuLockVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WmsApi {

    /**
     * 根据商品id查询库存信息
     * @param skuId 具体商品id
     */
    @GetMapping("wms/waresku/{skuId}")
    Resp<List<WareSkuEntity>> queryWareSkuBySkuId(@PathVariable("skuId")Long skuId);

    /**
     * 判断商品是否为空并锁住商品
     * @param skuLockVOS
     * @return
     */
    @PostMapping("wms/waresku")
    Resp<Object> checkAndLockStore(@RequestBody List<SkuLockVO> skuLockVOS);
}
