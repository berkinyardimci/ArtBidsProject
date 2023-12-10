package com.artbids.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@ToString
@Getter
public class AuctionNotFoundException extends RuntimeException {

    private String errorMessage;

    public AuctionNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
