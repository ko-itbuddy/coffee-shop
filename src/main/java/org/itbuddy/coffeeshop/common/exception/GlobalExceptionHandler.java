package org.itbuddy.coffeeshop.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 컨트롤러 단 Validation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
            null
        );
    }

    // 서비스, 도메인단 Validation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> BadRequest(IllegalArgumentException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            null
        );
    }


    // 이외의 오류
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> BadRequest(Exception e) {
        log.error("[ERROR] Internal Server Error, Message = {}", e.getMessage());
        return ApiResponse.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            e.getMessage(),
            null
        );
    }
}
