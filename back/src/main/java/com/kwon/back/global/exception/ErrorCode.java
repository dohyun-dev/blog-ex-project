package com.kwon.back.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 400
    VALIDATION_FAILURE("DBE-101", HttpStatus.BAD_REQUEST, "유효성 검증에 실패하였습니다."),
    DUPLICATED_EMAIL("DBE-102", HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    DUPLICATED_PHONE_NUMBER("DBE-103", HttpStatus.BAD_REQUEST, "중복된 휴대폰 번호입니다."),
    USER_NOT_FOUND("DBE-104", HttpStatus.BAD_REQUEST, "요청하신 회원이 존재하지 않습니다."),
    POST_NOT_FOUND("DBE-105", HttpStatus.BAD_REQUEST, "요청하신 게시글이 존재하지 않습니다."),

    // 인증 401
    LOGIN_FAILURE("DAE-101", HttpStatus.UNAUTHORIZED, "로그인을 실패하였습니다."),
    AUTHENTICATION_REQUIRED("DAE-102", HttpStatus.UNAUTHORIZED, "인증이 필요한 요청입니다."),
    TOKEN_EXPIRED_OR_INVALID("DAE-103", HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),
    ACCESS_DENIED("DAE-104", HttpStatus.UNAUTHORIZED, "접근이 거부되었습니다."),

    // 403
    PERMISSION_DENIED("DFE-301", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // 500 Internal Server Error
    DATABASE_ERROR("DBE-501", HttpStatus.INTERNAL_SERVER_ERROR, "서버에 에러가 발생하였습니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
