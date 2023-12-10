package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountIsNotActiveException extends RuntimeException {

    private String errorMessage;

    public AccountIsNotActiveException(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
