package com.gmp.inventory.configurations;

import com.gmp.inventory.client.ParkingClient;
import com.gmp.inventory.client.PermitClient;
import com.gmp.spring.bootstrap.SpringRetrofitServiceClientFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Retrofit client configuration for external services
 *
 * @author Mrigank Tandon
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RetrofitClientConfig {

    @Bean
    public ParkingClient parkingClient(SpringRetrofitServiceClientFactory springRetrofitServiceClientFactory) {
        log.info("Creating ParkingClient bean");
        return springRetrofitServiceClientFactory.getClientForService("parking_url", ParkingClient.class);
    }

    @Bean
    public PermitClient permitClient(SpringRetrofitServiceClientFactory springRetrofitServiceClientFactory) {
        log.info("Creating PermitClient bean");
        return springRetrofitServiceClientFactory.getClientForService("permit.url", PermitClient.class);
    }
}
