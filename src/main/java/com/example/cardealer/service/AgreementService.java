package com.example.cardealer.service;

import com.example.cardealer.model.Agreement;
import com.example.cardealer.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementService {
    private final AgreementRepository agreementRepository;

    @Autowired
    public AgreementService(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    public void save(Agreement agreement) {
        agreementRepository.save(agreement);
    }
}
