package com.gmp.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EntityScan(basePackages = "com.gmp.inventory.persistence.model")
@EnableJpaRepositories(basePackages = "com.gmp.inventory.repository.interfaces")
@EnableTransactionManagement
@EnableConfigurationProperties
@ConfigurationPropertiesScan({"com.gmp.spring", "com.gmp.inventory.configurations"})
@SpringBootApplication(exclude = {ActiveMQAutoConfiguration.class})
@ComponentScan({"com.gmp.inventory", "com.gmp.spring", "com.gmp.security"})
public class InventoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
}
