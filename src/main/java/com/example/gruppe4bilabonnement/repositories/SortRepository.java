package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarBrand;
import com.example.gruppe4bilabonnement.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SortRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Car> carRowMapper;

    @Autowired
    private RowMapper<Customer> customerRowMapper;

    // Sort customers by criteria
    // Placeholders (?) cannot be used on dynamic parts of the query
    public List<Customer> getSortedCustomersByCriteria(String sortBy, String sortOrder) {
        //String query = "SELECT * FROM customer ORDER BY ? ?;"; !Doesn't work!
        String query = "SELECT * FROM customer ORDER BY " + sortBy + " " + sortOrder + ";";
        return jdbcTemplate.query(query, customerRowMapper);
    }

    // Sort cars by criteria (Join car and car model tables)
    public List<Car> getSortedCarsByCriteria(String sortBy, String sortOrder) {
        String query = "SELECT * FROM car c JOIN car_model cm ON c.car_model_id = cm.id ORDER BY " + sortBy + " " + sortOrder + ";";
        return jdbcTemplate.query(query, carRowMapper);
    }

    // Sort car brands by criteria
    public List<CarBrand> getSortedCarBrandsByCriteria(String sortBy, String sortOrder) {
        String query = "SELECT * FROM car_brand ORDER BY " + sortBy + " " + sortOrder + ";";
        RowMapper<CarBrand> rowMapper = new BeanPropertyRowMapper<>(CarBrand.class);
        return jdbcTemplate.query(query, rowMapper);
    }
}
