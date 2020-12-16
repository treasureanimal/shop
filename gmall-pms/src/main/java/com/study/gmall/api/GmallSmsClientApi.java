package com.study.gmall.api;

import com.study.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("sms-service")
public interface GmallSmsClientApi  extends GmallSmsApi {
}
