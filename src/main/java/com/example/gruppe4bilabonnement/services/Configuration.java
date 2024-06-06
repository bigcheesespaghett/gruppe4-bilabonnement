package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.services.rowmappers.CarModelRowMapper;
import com.example.gruppe4bilabonnement.services.rowmappers.CarRowMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public CarModelRowMapper carModelRowMapper() {
        return new CarModelRowMapper();
    }

    @Bean
    public CarRowMapper carRowMapper() {
        return new CarRowMapper();
    }
}

