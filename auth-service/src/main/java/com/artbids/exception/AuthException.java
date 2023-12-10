package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@Getter
public class AuthException extends RuntimeException {


    //Auth ve UserPRofile için tek bir exception fırlatabiliriz.
    private String errorMessage;

    public AuthException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
