package com.artbids.exception;

import com.artbids.constant.ErrorCode;
import com.artbids.data.RestHeader;
import com.artbids.data.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<BaseResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return getBaseResponseResponseEntity(ex.toString(), ex.getErrorMessage());
    }

    @ExceptionHandler(AccountIsNotActiveException.class)
    public ResponseEntity<BaseResponse> handleAccountIsNotActiveException(AccountIsNotActiveException ex) {
        return getBaseResponseResponseEntity(ex.toString(), ex.getErrorMessage());
    }

    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<BaseResponse> handlePasswordNotMatchesException(PasswordNotMatchesException ex) {
        return getBaseResponseResponseEntity(ex.toString(), ex.getErrorMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<BaseResponse> handleInvalidTokenException(InvalidTokenException ex) {
        return getBaseResponseResponseEntity(ex.toString(), ex.getErrorMessage());
    }

    private ResponseEntity<BaseResponse> getBaseResponseResponseEntity(String s, String errorMessage) {
        BaseResponse baseResponse = new BaseResponse();
        ErrorDetail errorDetail = new ErrorDetail(ErrorCode.AUTH_EXCEPTION.getValue(),
                ErrorCode.AUTH_EXCEPTION.getReasonPhrase() + " " + s, new Date());
        baseResponse.setRestHeader(new RestHeader(false, errorMessage,errorDetail));
        return new ResponseEntity<>(baseResponse ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        ErrorDetail errorDetail = ErrorDetail.builder()
                .errorCode(ErrorCode.AUTH_EXCEPTION.getValue())
                .errorMessage(ex.getMessage())
                .timestamp(new Date())
                .validationErrors(ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(x -> x.getField(), z -> z.getDefaultMessage())))
                .build();
        baseResponse.setRestHeader(new RestHeader(false,"Hata",errorDetail));
        return new ResponseEntity<>(baseResponse ,HttpStatus.NOT_FOUND);
    }

}
