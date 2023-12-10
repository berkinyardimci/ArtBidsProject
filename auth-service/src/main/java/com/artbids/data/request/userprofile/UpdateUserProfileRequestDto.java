package com.artbids.data.request.userprofile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserProfileRequestDto {

    private String token;
    private String name;
    private String surName;
    private String about;
    private String avatar;
    private String phone;
    private LocalDate birthDate;
    private String username;
    private String email;
}
