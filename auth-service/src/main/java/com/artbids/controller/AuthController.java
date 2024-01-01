package com.artbids.controller;

import com.artbids.constant.SuccessMessages;
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
        response.setRestHeader(new RestHeader(true, SuccessMessages.REGISTER_SUCCESS.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto dto) {
        LoginResponse responseDto = authService.login(dto);
        responseDto.setRestHeader(new RestHeader(true,SuccessMessages.LOGIN_SUCCESS.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/changeStatus/{id}")
    public ResponseEntity<ChangeStatusResponse> changeStatus(@PathVariable Long id) {
        ChangeStatusResponse response = authService.changeStatusToActive(id);
        response.setRestHeader(new RestHeader(true,SuccessMessages.ACCOUNT_ACTIVATE_SUCCESS.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateEmail/{id}")
    public ResponseEntity<UpdateEmailResponse> updateEmail(@PathVariable Long id,@RequestBody UpdateEmailRequestDto dto) {
        UpdateEmailResponse updateEmailResponse = authService.updateEmail(id, dto);
        updateEmailResponse.setRestHeader(new RestHeader(true,SuccessMessages.CHANGE_EMAIL_SUCCESS.getMessage()));
        return new ResponseEntity<>(updateEmailResponse, HttpStatus.OK);
    }

    @PostMapping("/updateUsername/{id}")
    public ResponseEntity<UpdateUsernameResponse> updateUsername(@PathVariable Long id,@RequestBody UpdateUsernameRequestDto dto) {
        UpdateUsernameResponse updateUsernameResponse = authService.updateUsername(id,dto);
        updateUsernameResponse.setRestHeader(new RestHeader(true,SuccessMessages.CHANGE_USERNAME_SUCCESS.getMessage()));
        return new ResponseEntity<>(updateUsernameResponse, HttpStatus.OK);
    }
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<ChangePasswordResponse> updatePassword(@PathVariable Long id,@RequestBody ChangePasswordRequestDto dto) {
        ChangePasswordResponse changePasswordresponse = authService.changePassword(id,dto);
        changePasswordresponse.setRestHeader(new RestHeader(true,SuccessMessages.CHANGE_PASSWORD_SUCCESS.getMessage()));
        return new ResponseEntity<>(changePasswordresponse, HttpStatus.OK);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<ForgetPasswordResponse> forgetPassword(@RequestBody ForgetPasswordRequestDto dto) {
        ForgetPasswordResponse forgetPasswordResponse = authService.forgetPassword(dto);
        forgetPasswordResponse.setRestHeader(new RestHeader(true,SuccessMessages.CHANGE_PASSWORD_SUCCESS.getMessage()));
        return new ResponseEntity<>(forgetPasswordResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deleteProfile(@PathVariable Long id) {
        authService.deleteUserProfile(id);
        BaseResponse response = new BaseResponse();
        response.setRestHeader(new RestHeader(true,SuccessMessages.DELETE_PROFILE_SUCCESS.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
