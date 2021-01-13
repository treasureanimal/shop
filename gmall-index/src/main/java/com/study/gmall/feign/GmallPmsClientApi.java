package com.study.gmall.feign;

import com.study.gmall.pms.api.PmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("pms-service")
public interface GmallPmsClientApi extends PmsApi {
}
