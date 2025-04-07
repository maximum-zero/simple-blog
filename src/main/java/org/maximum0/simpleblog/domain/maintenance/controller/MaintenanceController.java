package org.maximum0.simpleblog.domain.maintenance.controller;

import org.maximum0.simpleblog.common.response.ApiResponse;
import org.maximum0.simpleblog.domain.maintenance.dto.MaintenanceStatusReq;
import org.maximum0.simpleblog.domain.maintenance.dto.MaintenanceStatusRes;
import org.maximum0.simpleblog.domain.maintenance.service.MaintenanceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
public class MaintenanceController {

    private final MaintenanceManager manager;

    public MaintenanceController(MaintenanceManager manager) {
        this.manager = manager;
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<MaintenanceStatusRes>> getStatus() {
        Boolean status = manager.isMaintaining();
        MaintenanceStatusRes res = new MaintenanceStatusRes(status);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PutMapping("/status/set")
    public ResponseEntity<ApiResponse<MaintenanceStatusRes>> setStatus(@RequestBody MaintenanceStatusReq req) {
        manager.setMaintaining(req.maintenance());
        MaintenanceStatusRes res = new MaintenanceStatusRes(manager.isMaintaining());
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}