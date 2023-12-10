package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@Getter
public class UserNotFoundException extends RuntimeException {

    private String errorMessage;

    public UserNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
