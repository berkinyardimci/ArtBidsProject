package com.artbids.controller;

import com.artbids.data.RestHeader;
import com.artbids.data.request.auth.*;
import com.artbids.data.response.BaseResponse;
import com.artbids.data.response.auth.*;
import com.artbids.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequestDto dto) {
        RegisterResponse response = authService.register(dto);
        response.setRestHeader(new RestHeader(true,"Kayıt Başarılı",null));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto dto) {
        LoginResponse responseDto = authService.login(dto);
        responseDto.setRestHeader(new RestHeader(true,"Success Login"));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/changeStatus/{id}")
    public ResponseEntity<ChangeStatusResponse> changeStatus(@PathVariable Long id) {
        ChangeStatusResponse response = authService.changeStatusToActıve(id);
        response.setRestHeader(new RestHeader(true,"Hesap Aktive Edildi",null));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateEmail")
    public ResponseEntity<UpdateEmailResponse> updateEmail(@RequestBody UpdateEmailRequestDto dto) {
        UpdateEmailResponse updateEmailResponse = authService.updateEmail(dto);
        updateEmailResponse.setRestHeader(new RestHeader(true,"Email Güncelleme Başarılı",null));
        return new ResponseEntity<>(updateEmailResponse, HttpStatus.OK);
    }

    @PostMapping("/updateUsername")
    public ResponseEntity<UpdateUsernameResponse> updateUsername(@RequestBody UpdateUsernameRequestDto dto) {
        UpdateUsernameResponse updateUsernameResponse = authService.updateUsername(dto);
        updateUsernameResponse.setRestHeader(new RestHeader(true,"Username Güncelleme Başarılı",null));
        return new ResponseEntity<>(updateUsernameResponse, HttpStatus.OK);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<ChangePasswordResponse> updateEmail(@RequestBody ChangePasswordRequestDto dto) {
        ChangePasswordResponse changePasswordresponse = authService.changePassword(dto);
        changePasswordresponse.setRestHeader(new RestHeader(true,"Şifre Güncelleme Başarılı",null));
        return new ResponseEntity<>(changePasswordresponse, HttpStatus.OK);
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<ForgetPasswordResponse> updateEmail(@RequestBody ForgetPasswordRequestDto dto) {
        ForgetPasswordResponse forgetPasswordResponse = authService.forgetPassword(dto);
        forgetPasswordResponse.setRestHeader(new RestHeader(true,"Şifre Güncelleme Başarılı",null));
        return new ResponseEntity<>(forgetPasswordResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{token}")
    public ResponseEntity<BaseResponse> deleteProfile(@PathVariable Long id) {
        authService.deleteUserProfile(id);
        BaseResponse response = new BaseResponse();
        response.setRestHeader(new RestHeader(true,"Silme işlemi Başarılı",null));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
