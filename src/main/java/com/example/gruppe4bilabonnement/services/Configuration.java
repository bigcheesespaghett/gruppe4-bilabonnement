package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.services.rowmappers.CarRowMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public RowMapper<Car> carRowMapper() {
        return new CarRowMapper();
    }

    @Bean
    public RowMapper<Customer> customerBeanPropertyRowMapper() {
        return new BeanPropertyRowMapper<>(Customer.class);
    }
}

