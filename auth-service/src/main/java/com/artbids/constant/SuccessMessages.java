package com.artbids.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessMessages {

    REGISTER_SUCCESS("Kayıt Başarılı"),
    LOGIN_SUCCESS("Giriş Başarılı"),
    ACCOUNT_ACTIVATE_SUCCESS("Hesap Aktive Edildi"),
    CHANGE_EMAIL_SUCCESS("Email Güncelleme Başarılı"),
    CHANGE_USERNAME_SUCCESS("Username Güncelleme Başarılı"),
    CHANGE_PASSWORD_SUCCESS("Şifre Güncelleme Başarılı"),
    DELETE_PROFILE_SUCCESS("Silme işlemi Başarılı");


    private final String message;
}
