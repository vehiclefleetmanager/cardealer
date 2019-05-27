package com.example.cardealer.service;

import com.example.cardealer.mappers.AgreementMapper;
import com.example.cardealer.mappers.CarMapper;
import com.example.cardealer.mappers.CustomerMapper;
import com.example.cardealer.model.Agreement;
import com.example.cardealer.model.dtos.AgreementDto;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.CustomerDto;
import com.example.cardealer.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgreementService {
    private final AgreementRepository agreementRepository;
    private AgreementMapper agreementMapper;
    private CarMapper carMapper;
    private CustomerMapper customerMapper;

    @Autowired
    public AgreementService(AgreementRepository agreementRepository,
                            AgreementMapper agreementMapper,
                            CarMapper carMapper,
                            CustomerMapper customerMapper) {
        this.agreementRepository = agreementRepository;
        this.agreementMapper = agreementMapper;
        this.carMapper = carMapper;
        this.customerMapper = customerMapper;
    }

    public void save(Agreement agreement) {
        agreementRepository.save(agreement);
    }

    public List<AgreementDto> getAgreementsDto() {
        List<AgreementDto> databaseAgreement = agreementRepository
                .findAll()
                .stream()
                .map(agreementMapper::map)
                .collect(Collectors.toList());
        addPersonalDataCustomerAndBasicCarInfo(databaseAgreement);
        return databaseAgreement;
    }

    private void addPersonalDataCustomerAndBasicCarInfo(List<AgreementDto> databaseCollection) {
        for (AgreementDto agreementDto : databaseCollection) {
            CarDto carDto = carMapper.map(agreementDto.getCar());
            agreementDto.setCarBodyNumber(carDto.getBodyNumber());
            agreementDto.setCarMark(carDto.getMark());
            agreementDto.setCarModel(carDto.getModel());
            CustomerDto customerDto = customerMapper.map(agreementDto.getCustomer());
            agreementDto.setCustomerFirstName(customerDto.getFirstName());
            agreementDto.setCustomerLastName(customerDto.getLastName());
        }
    }
}
