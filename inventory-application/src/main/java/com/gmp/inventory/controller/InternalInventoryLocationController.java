package com.gmp.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/internal/v1/inventory-location")
@RequiredArgsConstructor
public class InternalInventoryLocationController {

    // Internal APIs for locations, location-parking-maps, parking-metadata to be added here
}
