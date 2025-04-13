package org.maximum0.simpleblog.core.exception;

import org.maximum0.simpleblog.core.notification.NotificationLevel;

public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final NotificationLevel level;

    public BaseException(ErrorCode errorCode) {
        this(errorCode, NotificationLevel.ERROR);
    }

    public BaseException(ErrorCode errorCode, NotificationLevel level) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.level = level;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public NotificationLevel getLevel() {
        return level;
    }

}