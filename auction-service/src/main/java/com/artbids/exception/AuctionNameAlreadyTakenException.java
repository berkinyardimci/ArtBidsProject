package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@Getter
public class AuctionNameAlreadyTakenException extends RuntimeException {

    private String errorMessage;

    public AuctionNameAlreadyTakenException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
