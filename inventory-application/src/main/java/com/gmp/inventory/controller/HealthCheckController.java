package com.gmp.inventory.controller;

import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("v1/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("gmp-inventory-management is Up!", HttpStatus.OK);
    }
}
