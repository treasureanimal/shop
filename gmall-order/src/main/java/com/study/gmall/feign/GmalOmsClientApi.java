package com.study.gmall.feign;

import com.study.gmall.oms.api.OmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("oms-service")
public interface GmalOmsClientApi extends OmsApi {
}
