package com.example.cardealer.controller;

import com.example.cardealer.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sale")
public class AgreementController {

    private final AgreementRepository agreementRepository;

    @Autowired
    public AgreementController(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }


}
