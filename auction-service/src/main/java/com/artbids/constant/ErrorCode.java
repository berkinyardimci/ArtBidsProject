package com.artbids.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    AUCTION_EXCEPTION(1001,"Auction Exception");


    private final int value;
    private final String reasonPhrase;
}
