package org.maximum0.simpleblog.core.exception;

import org.maximum0.simpleblog.core.notification.NotificationLevel;
import org.maximum0.simpleblog.core.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final Notifier notifier;

    public GlobalExceptionHandler(Notifier notifier) {
        this.notifier = notifier;
    }

    /**
     * BaseException 발생 시 처리
     * - ErrorCode 기반 응답 반환
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return handleException(ex.getErrorCode(), ex.getLevel(), ex, "BaseException");
    }

    /**
     * 처리되지 않은 예외(Exception) 발생 시 처리
     * - INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return handleException(ErrorCode.INTERNAL_SERVER_ERROR, NotificationLevel.CRITICAL, ex, "Unhandled Exception");
    }


    /**
     * 공통 예외 처리 로직
     * - 로그 기록
     * - 알림 전송 (NotificationLevel > NONE)
     * - ErrorResponse 생성 및 반환
     */
    private ResponseEntity<ErrorResponse> handleException(
            ErrorCode code,
            NotificationLevel level,
            Exception ex,
            String logPrefix
    ) {
        log.error("⚠️ {}: [{}] {} - {}", logPrefix, code.getCode(), code.getMessage(), ex.getMessage(), ex);

        if (level != NotificationLevel.NONE) {
            notifier.notify(buildNotificationTitle(code.getCode(), code.getMessage()), ex.getMessage(), level);
        }

        ErrorResponse response = new ErrorResponse(code.getCode(), code.getMessage());
        return ResponseEntity
                .status(code.getStatus())
                .body(response);
    }

    /**
     * 알림 전송용 메시지 Title 구성
     * - 예외 코드, 메시지를 포함한 문자열 반환
     */
    private String buildNotificationTitle(String code, String message) {
        return String.format("[%s] %s", code, message);
    }
//    private String buildNotificationMessage(String code, String message, NotificationLevel level, Exception ex) {
//        return String.format("[%s] %s - Level: %s\nDetail: %s", code, message, level, ex.getMessage());
//    }

}