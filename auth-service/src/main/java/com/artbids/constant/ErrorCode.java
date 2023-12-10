package com.artbids.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    AUTH_EXCEPTION(1001,"Auth Exception");


    private final int value;
    private final String reasonPhrase;
}
