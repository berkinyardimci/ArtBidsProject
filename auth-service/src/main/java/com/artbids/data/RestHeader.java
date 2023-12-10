package com.artbids.data;

import com.artbids.exception.ErrorDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestHeader {

    private boolean success;
    private String message;

    private ErrorDetail errorDetail;

    public RestHeader(Boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
