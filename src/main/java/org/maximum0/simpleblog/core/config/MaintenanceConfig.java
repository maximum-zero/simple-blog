package org.maximum0.simpleblog.core.config;

import org.maximum0.simpleblog.domain.maintenance.service.MaintenanceManager;
import org.maximum0.simpleblog.domain.maintenance.service.MemoryMaintenanceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaintenanceConfig {

    @Bean
    public MaintenanceManager maintenanceManager() {
        return new MemoryMaintenanceManager();
    }
}