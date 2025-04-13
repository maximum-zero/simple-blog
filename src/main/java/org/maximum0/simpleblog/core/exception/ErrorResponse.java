package org.maximum0.simpleblog.core.exception;

public record ErrorResponse(
        String code,
        String message
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}