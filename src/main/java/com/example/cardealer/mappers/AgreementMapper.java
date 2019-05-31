package com.example.cardealer.mappers;

import com.example.cardealer.model.Agreement;
import com.example.cardealer.model.dtos.AgreementDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class AgreementMapper implements Mapper<Agreement, AgreementDto> {
    @Override
    public AgreementDto map(Agreement from) {
        return AgreementDto.builder()
                .id(from.getId())
                .content(from.getContent())
                .customer(from.getCustomer())
                .car(from.getCar())
                .transaction(from.getTransaction())
                //.invoiceList(new ArrayList<>(from.getInvoices()))
                .build();
    }

    @Override
    public Agreement reverse(AgreementDto to) {
        return Agreement.builder()
                .id(to.getId())
                .content(to.getContent())
                .customer(to.getCustomer())
                .car(to.getCar())
                .transaction(to.getTransaction())
                //.invoices(new HashSet<>(to.getInvoiceList()))
                .build();
    }
}
