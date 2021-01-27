package com.study.gmall.feign;

import com.study.gmall.cart.api.CartApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("cart-service")
public interface GmallCartClientApi extends CartApi {
}
