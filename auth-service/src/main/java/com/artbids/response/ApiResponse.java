package com.artbids.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T>{

    private LocalDateTime dateTime;
    private HttpStatus httpStatus;
    private int statusCode;
    private  T data;
    private String message;



}
