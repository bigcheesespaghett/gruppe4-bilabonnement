package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarBrand;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.repositories.SortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortService {
    @Autowired
    private SortRepository sortRepository;

    // Sort order toggle
    public String toggleSortOrder(String sortOrder) {
        if (sortOrder.equals("ASC")) {
            return "DESC";
        } else {
            return "ASC";
        }
    }

    // Order arrow toggle
    public String getOrderArrow(String sortOrder) {
        if (sortOrder.equals("ASC")) {
            return "▲";
        } else {
            return "▼";
        }
    }

    // Sorted customers
    public List<Customer> getSortedCustomersByCriteria(String sortBy, String sortOrder) {
        return sortRepository.getSortedCustomersByCriteria(sortBy, sortOrder);
    }

    // Sorted cars
    public List<Car> getSortedCarsByCriteria(String sortBy, String sortOrder) {
        return sortRepository.getSortedCarsByCriteria(sortBy, sortOrder);
    }

    // Sorted car brands
    public List<CarBrand> getSortedCarBrandsByCriteria(String sortBy, String sortOrder) {
        return sortRepository.getSortedCarBrandsByCriteria(sortBy, sortOrder);
    }
}
