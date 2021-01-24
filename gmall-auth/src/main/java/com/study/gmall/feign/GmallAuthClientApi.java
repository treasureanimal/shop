package com.study.gmall.feign;

import com.study.gmall.ums.api.UmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ums-service")
public interface GmallAuthClientApi extends UmsApi {

}
