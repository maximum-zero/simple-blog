package org.maximum0.simpleblog.core.health;

import org.maximum0.simpleblog.domain.maintenance.service.MaintenanceManager;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceHealthIndicator implements HealthIndicator {

    private final MaintenanceManager maintenanceManager;

    public MaintenanceHealthIndicator(MaintenanceManager maintenanceManager) {
        this.maintenanceManager = maintenanceManager;
    }

    @Override
    public Health health() {
        if (maintenanceManager.isMaintaining()) {
            return Health.down().withDetail("message", "점검 상태입니다.").build();
        }
        return Health.up().withDetail("message", "운영 상태입니다.").build();
    }
}
