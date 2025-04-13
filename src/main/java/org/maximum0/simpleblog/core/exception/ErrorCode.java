package org.maximum0.simpleblog.core.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_REQUEST("C400", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("C404", "리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("C500", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}
