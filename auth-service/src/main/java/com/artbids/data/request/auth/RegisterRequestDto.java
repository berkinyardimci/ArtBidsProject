package com.artbids.data.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotBlank
    //@UniqueUsername
    private String username;

    @Email
    //@UniqueEmail
    private String email;

    @Size(min = 5, max = 32)
    private String password;

    @Size(min = 5, max = 32)
    private String rePassword;
}
