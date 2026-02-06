package com.gmp.inventory.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Internal REST controller for Inventory (items), Inventory Pricing and Inventory Fulfilment.
 * Add internal-only APIs here.
 */
@RestController
@Slf4j
@RequestMapping("/internal/v1/inventory")
@RequiredArgsConstructor
public class InternalInventoryController {

    // Internal APIs for inventory, pricing, fulfilment, inventory-mapping to be added here
}
