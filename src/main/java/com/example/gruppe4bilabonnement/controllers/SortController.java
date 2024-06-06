package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarBrand;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.services.CarService;
import com.example.gruppe4bilabonnement.services.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SortController {
    @Autowired
    private SortService sortService;

    @Autowired
    private CarService carService;

    @PostMapping("/sort_customers")
    public String sortCustomers(@RequestParam String sortBy, @RequestParam String sortOrder, Model model) {
        String newSortOrder = sortService.toggleSortOrder(sortOrder);
        String orderArrow = sortService.getOrderArrow(newSortOrder);

        List<Customer> customers = sortService.getSortedCustomersByCriteria(sortBy, newSortOrder);

        model.addAttribute("customers", customers);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", newSortOrder);
        model.addAttribute("orderArrow", orderArrow);
        return "salesperson/customer_overview";
    }

    @PostMapping("/sort_cars")
    public String sortCars(@RequestParam String sortBy, @RequestParam String sortOrder, Model model) {
        String newSortOrder = sortService.toggleSortOrder(sortOrder);
        String orderArrow = sortService.getOrderArrow(newSortOrder);

        List<Car> cars = sortService.getSortedCarsByCriteria(sortBy, newSortOrder);

        model.addAttribute("cars", cars);
        model.addAttribute("carService", carService);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", newSortOrder);
        model.addAttribute("orderArrow", orderArrow);
        return "salesperson/car/car_overview";
    }

    @PostMapping("/sort_car_brands")
    public String sortCarBrands(@RequestParam String sortBy, @RequestParam String sortOrder, Model model) {
        String newSortOrder = sortService.toggleSortOrder(sortOrder);
        String orderArrow = sortService.getOrderArrow(newSortOrder);

        List<CarBrand> carBrands = sortService.getSortedCarBrandsByCriteria(sortBy, newSortOrder);

        model.addAttribute("carBrands", carBrands);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", newSortOrder);
        model.addAttribute("orderArrow", orderArrow);
        return "salesperson/car/carbrand/car_brand_overview";
    }
}
