package com.study.gmall.feign;

import com.study.gmall.wms.api.WmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("wms-service")
public interface GmallWmsClient extends WmsApi {
}
