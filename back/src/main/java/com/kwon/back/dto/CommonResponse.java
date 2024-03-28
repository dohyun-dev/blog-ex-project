package com.kwon.back.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class CommonResponse<T> {

    private T data;

    public ResponseEntity toResponseEntity(HttpStatus status, T data) {
        return ResponseEntity.status(status).body(this);
    }
}
