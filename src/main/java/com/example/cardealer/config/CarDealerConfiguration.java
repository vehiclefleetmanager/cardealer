package com.example.cardealer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarDealerConfiguration {

    @Bean
    public Clock clock() {
        return new SystemClock();
    }
}
