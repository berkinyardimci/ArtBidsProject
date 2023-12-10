package com.artbids.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountAlreadyActive extends RuntimeException {

    private String errorMessage;

    public AccountAlreadyActive(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}