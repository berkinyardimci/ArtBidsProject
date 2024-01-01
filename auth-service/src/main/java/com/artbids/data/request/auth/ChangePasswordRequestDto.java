package com.artbids.data.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequestDto {

    private String oldPassword;
    private String newPassword;
    private String reNewPassword;

}
