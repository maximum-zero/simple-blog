package org.maximum0.simpleblog.controller.maintenance;

import org.maximum0.simpleblog.domain.maintenance.MaintenanceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class MaintenanceController {

    private final MaintenanceManager manager;

    public MaintenanceController(MaintenanceManager manager) {
        this.manager = manager;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getStatus() {
        return ResponseEntity.ok(Map.of("result", manager.isMaintaining()));
    }

    @PutMapping("/status/set")
    public ResponseEntity<Map<String, Boolean>> setStatus(@RequestParam boolean status) {
        manager.setMaintaining(status);
        return ResponseEntity.ok(Map.of("result", manager.isMaintaining()));
    }
}