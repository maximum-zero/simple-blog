package org.maximum0.simpleblog.common.response;

public class ApiResponse<T> {
    private final int code;
    private final String message;
    private final T data;

    private ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(ApiResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiResponseCode.SUCCESS, data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ApiResponseCode.SUCCESS, null);
    }

    public static <T> ApiResponse<T> error(ApiResponseCode errorCode) {
        return new ApiResponse<>(errorCode, null);
    }

    public static <T> ApiResponse<T> of(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
