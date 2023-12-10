package com.artbids.data.response.auth;


import com.artbids.data.response.BaseResponse;
import com.artbids.entity.enums.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class LoginResponse extends BaseResponse {

    private String token;
    private Set<ERole> roles;
}
