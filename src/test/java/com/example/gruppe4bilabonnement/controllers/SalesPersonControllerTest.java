package com.example.gruppe4bilabonnement.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SalesPersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SalesPersonService salesPersonService;

    @Mock
    private LeaseAgreementService leaseAgreementService;

    @Mock
    private CarService carService;

    @Mock
    private MechanicService mechanicService;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private SalesPersonController salesPersonController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(salesPersonController).build();
    }

    @Test
    public void testFrontpage() throws Exception {
        mockMvc.perform(get("/salesperson/frontpage"))
                .andExpect(status().isOk())
                .andExpect(view().name("salesperson/frontpage"));
    }

    @Test
    public void testInsert() throws Exception {
        mockMvc.perform(get("/salesperson/new_customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("salesperson/create_customer"));
    }

    @Test
    public void testInsertCustomer() throws Exception {
        String email = "arnold@example.com";
        String phoneNumber = "12345678";
        int zipCode = 90210;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setEmail(email);

        when(salesPersonService.isEmailRegistered(email)).thenReturn(false);
        when(salesPersonService.isZipCodeValid(zipCode)).thenReturn(true);
        when(salesPersonService.getCustomerByEmail(email)).thenReturn(mockCustomer);

        mockMvc.perform(post("/salesperson/insert")
                        .param("firstName", "Arnold")
                        .param("lastName", "Schwarzenegger")
                        .param("phoneNumber", phoneNumber)
                        .param("email", email)
                        .param("address", "123 Muscle Street")
                        .param("zipCode", String.valueOf(zipCode)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/salesperson/new_lease_agreement?customerId=1"));

        verify(salesPersonService).insert("Arnold", "Schwarzenegger", phoneNumber, email, "123 Muscle Street", zipCode);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        String oldEmail = "arnold@example.com";
        String newEmail = "arnold.new@example.com";
        String phoneNumber = "87654321";
        int zipCode = 9021;
        Customer customer = new Customer();
        customer.setId(1);
        customer.setEmail(oldEmail);

        when(salesPersonService.getCustomerById(1)).thenReturn(customer);
        when(salesPersonService.getErrorMessageForInvalidEmail(newEmail, customer)).thenReturn(null);
        when(salesPersonService.getErrorMessageForInvalidZipCode(zipCode)).thenReturn(null);

        mockMvc.perform(post("/salesperson/update_customer")
                        .param("id", "1")
                        .param("firstName", "Arnold")
                        .param("lastName", "Schwarzenegger")
                        .param("phoneNumber", phoneNumber)
                        .param("email", newEmail)
                        .param("address", "456 Fitness Blvd")
                        .param("zipCode", String.valueOf(zipCode))
                        .param("origin", "overview"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/salesperson/customer_overview"));

        verify(salesPersonService).update(1, "Arnold", "Schwarzenegger", phoneNumber, newEmail, "456 Fitness Blvd", zipCode);
    }
}
