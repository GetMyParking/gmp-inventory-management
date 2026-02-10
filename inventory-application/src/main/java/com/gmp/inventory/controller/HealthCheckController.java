package com.gmp.inventory.controller;

import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping(value = "/inventory-management/v1/healthCheck", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("gmp-inventory-management is Up!", HttpStatus.OK);
    }
}
