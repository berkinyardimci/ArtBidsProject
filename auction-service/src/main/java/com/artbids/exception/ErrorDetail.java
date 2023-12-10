package com.artbids.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErrorDetail {

    private int errorCode;
    private String errorMessage;
    private Date timestamp;
    private Map<String, String> validationErrors = null;

    public ErrorDetail(int errorCode, String errorMessage, Date timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }
}
