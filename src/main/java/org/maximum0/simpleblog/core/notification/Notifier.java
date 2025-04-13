package org.maximum0.simpleblog.core.notification;

public interface Notifier {
    void notify(String title, String message, NotificationLevel level);
}
