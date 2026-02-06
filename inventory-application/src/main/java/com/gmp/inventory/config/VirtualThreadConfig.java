package com.gmp.inventory.config;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class VirtualThreadConfig {

    private ExecutorService virtualThreadExecutor;

    @Bean(name = "virtualThreadExecutor")
    @ConditionalOnProperty(name = "spring.threads.virtual.enabled", havingValue = "true")
    public ExecutorService virtualThreadExecutor() {
        this.virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
        return virtualThreadExecutor;
    }
}
