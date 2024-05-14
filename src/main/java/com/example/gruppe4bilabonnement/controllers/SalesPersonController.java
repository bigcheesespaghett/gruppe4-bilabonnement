package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.Customer;

import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.services.CarService;
import com.example.gruppe4bilabonnement.services.LeaseAgreementService;
import com.example.gruppe4bilabonnement.services.SalesPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class SalesPersonController {
    @Autowired
    private SalesPersonService salesPersonService;
    @Autowired
    private LeaseAgreementService leaseAgreementService;
    @Autowired
    private CarService carService;


    @GetMapping("/new_customer")
    public String insert() {
        return "/salesperson/create_customer";
    }

    //Customer creation
    @PostMapping("/insert")
    public String insert(Model model, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
                         @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {

        boolean isEmailRegistered = salesPersonService.isEmailRegistered(email);

        if (isEmailRegistered) {
            // Send an invalid message if e-mail is already registered in the system
            model.addAttribute("emailAlreadyRegistered", "E-mail er allerede i brug");
            return "create_customer";
        } else if (!email.contains(".")) {
            // Send an invalid message if e-mail doesn't contain "."
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
            return "create_customer";
        } else {
            // Send a success message if customer is created
            salesPersonService.insert(firstName, lastName, phoneNumber, email, address, zipCode);
            model.addAttribute("success", "Kunde tilføjet");
            return "redirect:/salesperson/new_lease";
        }
    }

    // Prepare customer update
    @GetMapping("/prepare_update/{id}")
    public String prepareUpdate(@PathVariable int id, Model model) {
        model.addAttribute("customer", salesPersonService.prepareUpdate(id));
        return "salesperson/update_customer";
    }

    // Update customer
    @PostMapping("/update_customer")
    public String update(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {
        salesPersonService.update(id, firstName, lastName, phoneNumber, email, address, zipCode);
        return "redirect:/salesperson/customer_overview";
    }

    //Show all customers
    @GetMapping("/customer_overview")
    public String showCustomers(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<Customer> customers = salesPersonService.getAllCustomers();
            model.addAttribute("customers", customers);
            return "salesperson/customer_overview";
        } else {
            return "redirect:/";
        }
    }

    //Prepare customer deletion
    @GetMapping("/confirm_delete_customer/{id}")
    public String confirmDelete(@PathVariable int id, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            model.addAttribute("customer", customer);
            return "salesperson/delete_customer";
        } else {
            return "redirect:/";
        }
    }

    //Customer deletion
    @PostMapping("/delete_customer")
    public String delete(@RequestParam int id) {
        salesPersonService.deleteCustomerById(id);
        return "redirect:/salesperson/customer_overview";
    }


    //Show selected customer profile
    @GetMapping("/customer_profile/{id}")
    public String customerProfile(@PathVariable int id, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Customer customer = salesPersonService.getCustomerById(id);
            model.addAttribute("customer", customer);
            return "salesperson/customer_profile";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/new_lease_agreement")
    public String showNewLeaseAgreementForm(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        model.addAttribute("carService", carService);
        return "salesperson/new_lease_agreement";
    }

    @PostMapping("/create_lease_agreement")
    public String createLeaseAgreement(@RequestParam int customerId, @ModelAttribute LeaseAgreement leaseAgreement) {
        leaseAgreementService.createLeaseAgreement(customerId, leaseAgreement);
        return "redirect:/salesperson/customer_overview";
    }

    @GetMapping("/lease_agreement/{id}")
    public String showLeaseAgreementDetails(@PathVariable int id, Model model) {
        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(id);
        if (leaseAgreement != null) {
            model.addAttribute("leaseAgreement", leaseAgreement);
            return "salesperson/lease_agreement_details";
        } else {
            return "redirect:/salesperson/customer_overview";
        }
    }

    @GetMapping("/lease_agreement")
    public String showAllLeaseAgreements(Model model) {
        List<LeaseAgreement> leaseAgreements = leaseAgreementService.getAllLeaseAgreements();
        model.addAttribute("leaseAgreements", leaseAgreements);
        return "lease_agreement";
    }

    @GetMapping("/update_lease_agreement/{id}")
    public String showUpdateLeaseAgreementForm(@PathVariable int id, Model model) {
        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(id);
        if (leaseAgreement != null) {
            model.addAttribute("leaseAgreement", leaseAgreement);
            return "salesperson/update_lease_agreement";
        } else {
            return "redirect:/salesperson/customer_overview";
        }
    }

    @PostMapping("/update_lease_agreement")
    public String updateLeaseAgreement(@ModelAttribute LeaseAgreement leaseAgreement) {
        leaseAgreementService.updateLeaseAgreement(leaseAgreement);
        return "redirect:/salesperson/customer_overview";
    }

    @PostMapping("/delete_lease_agreement/{id}")
    public String deleteLeaseAgreement(@PathVariable int id) {
        leaseAgreementService.deleteLeaseAgreement(id);
        return "redirect:/salesperson/customer_overview";
    }
}