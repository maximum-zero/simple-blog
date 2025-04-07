package org.maximum0.simpleblog.domain.maintenance.service;

import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryMaintenanceManager implements MaintenanceManager {
    private final AtomicBoolean maintenance = new AtomicBoolean(false);

    @Override
    public boolean isMaintaining() {
        return maintenance.get();
    }

    @Override
    public void setMaintaining(boolean maintaining) {
        maintenance.set(maintaining);
    }
}