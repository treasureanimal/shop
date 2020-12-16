package com.study.gmall.feign;

import com.study.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("sms-service")
public interface GmallSmsClientApi  extends GmallSmsApi {
}
