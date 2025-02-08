package com.hotel.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	@LoadBalanced   // Enables client-side load balancing
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
