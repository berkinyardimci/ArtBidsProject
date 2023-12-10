package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InvalidTokenException extends RuntimeException {

    private String errorMessage;
    public InvalidTokenException(String errorMessage) {

        this.errorMessage = errorMessage;
    }
}
