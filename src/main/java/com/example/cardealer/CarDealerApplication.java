package com.example.cardealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@SpringBootApplication
public class CarDealerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarDealerApplication.class, args);
    }

}
