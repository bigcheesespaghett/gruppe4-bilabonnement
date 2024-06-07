package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.repositories.LeaseAgreementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaseAgreementServiceTest {

    @InjectMocks
    private LeaseAgreementService leaseAgreementService;

    @Mock
    private LeaseAgreementRepository leaseAgreementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLeaseAgreement_validDates() {
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        leaseAgreement.setStartDate(LocalDate.of(2023, 1, 1));
        leaseAgreement.setEndDate(LocalDate.of(2023, 12, 31));
        leaseAgreement.setCarId(1);
        leaseAgreement.setCustomerId(1);

        leaseAgreementService.createLeaseAgreement(1, leaseAgreement);

        verify(leaseAgreementRepository, times(1)).createLeaseAgreement(1, leaseAgreement);
    }

    @Test
    void testCreateLeaseAgreement_invalidDates() {
        LeaseAgreement leaseAgreement = new LeaseAgreement();
        leaseAgreement.setStartDate(LocalDate.of(2023, 12, 31));
        leaseAgreement.setEndDate(LocalDate.of(2023, 1, 1));
        leaseAgreement.setCarId(1);
        leaseAgreement.setCustomerId(1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                leaseAgreementService.createLeaseAgreement(1, leaseAgreement)
        );

        assertEquals("Slutdato skal være efter startdato", exception.getMessage());
        verify(leaseAgreementRepository, times(0)).createLeaseAgreement(1, leaseAgreement);
    }
}
