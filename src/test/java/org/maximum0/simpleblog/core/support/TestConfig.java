package org.maximum0.simpleblog.core.support;


import org.maximum0.simpleblog.core.notification.Notifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {
    @Bean
    public Notifier notifier() {
        return mock(Notifier.class);
    }
}