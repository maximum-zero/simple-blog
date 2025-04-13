package org.maximum0.simpleblog.core.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class DiscordNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(DiscordNotifier.class);
    private final WebClient webClient = WebClient.create();

    private final String infoWebhookUrl;
    private final String errorWebhookUrl;

    public DiscordNotifier(
            @Value("${notification.discord.webhook-urls.info}") String infoWebhookUrl,
            @Value("${notification.discord.webhook-urls.error}") String errorWebhookUrl
    ) {
        this.infoWebhookUrl = infoWebhookUrl;
        this.errorWebhookUrl = errorWebhookUrl;
    }

    @Override
    public void notify(String title, String message, NotificationLevel level) {
        if (level == NotificationLevel.NONE) return;

        String webhookUrl = getWebhookUrl(level);
        if (!StringUtils.hasText(webhookUrl)) {
            log.warn("🔕 DiscordNotifier - No webhook URL configured for level: {}", level);
            return;
        }

        String content = String.format("%s **[%s] %s**\n%s", getEmoji(level), level.name(), title, message);
        log.info("🔔 [DISCORD][{}] Title: {}, Message: {}", level.name(), title, message);

        webClient.post()
                .uri(webhookUrl)
                .bodyValue(new DiscordMessage(content))
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(); // 비동기 처리
    }

    private String getWebhookUrl(NotificationLevel level) {
        return switch (level) {
            case INFO -> infoWebhookUrl;
            case ERROR, CRITICAL -> errorWebhookUrl;
            default -> null;
        };
    }

    private String getEmoji(NotificationLevel level) {
        return switch (level) {
            case INFO -> "ℹ️";
            case ERROR -> "⚠️";
            case CRITICAL -> "🔥";
            default -> "";
        };
    }

    record DiscordMessage(String content) {}
}

