package com.kwon.back.dto;

import com.kwon.back.exception.CustomException;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(String code, String message) {

    public static ErrorResponse of(CustomException customException) {
        return new ErrorResponse(
                customException.getErrorCode().getCode(),
                customException.getErrorCode().getMessage());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomException exception) {
        ErrorResponse errorResponse = ErrorResponse.of(exception);
        return ResponseEntity
                .status(exception.getErrorCode().getStatus())
                .body(errorResponse);
    }
}
