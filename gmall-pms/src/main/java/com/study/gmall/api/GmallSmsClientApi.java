package com.study.gmall.api;

import com.study.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface GmallSmsClientApi  extends GmallSmsApi {


}
