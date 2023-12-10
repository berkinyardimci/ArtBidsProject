package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class PasswordNotMatchesException extends RuntimeException {

    private String errorMessage;

    public PasswordNotMatchesException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}