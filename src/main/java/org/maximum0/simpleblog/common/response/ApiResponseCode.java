package org.maximum0.simpleblog.common.response;

public enum ApiResponseCode {

    SUCCESS(200, "성공"),
    BAD_REQUEST(400, "요청 형식이 올바르지 않습니다"),
    UNAUTHORIZED(401, "인증이 필요합니다"),
    FORBIDDEN(403, "접근 권한이 없습니다"),
    NOT_FOUND(404, "요청하신 리소스를 찾을 수 없습니다"),
    INTERNAL_ERROR(500, "서버 내부 오류가 발생했습니다");

    private final int code;
    private final String message;

    ApiResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
