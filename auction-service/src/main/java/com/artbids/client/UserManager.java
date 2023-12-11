package com.artbids.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:7075/userprofile" ,name = "post-user")
public interface UserManager {

    @GetMapping("/getUserIdFromToken/{token}")
    Long getUserIdFromUserProfileWithToken(@PathVariable String token);
}
