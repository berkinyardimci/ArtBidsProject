package com.artbids.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
