package com.artbids.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-user", url = "${auction-service.auth-service-url}")
public interface AuthServiceClient {

    @GetMapping("/getUserIdFromToken/{token}")
    Long getUserIdFromUserProfileWithToken(@PathVariable String token);
}
