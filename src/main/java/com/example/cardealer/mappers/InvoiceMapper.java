package com.example.cardealer.mappers;

import com.example.cardealer.model.Invoice;
import com.example.cardealer.model.dtos.InvoiceDto;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper implements Mapper<Invoice, InvoiceDto> {
    @Override
    public InvoiceDto map(Invoice from) {

        return InvoiceDto.builder()
                .id(from.getId())
                .invoiceNumber(from.getInvoiceNumber())
                .price(from.getPrice())
                .placeOfIssue(from.getPlaceOfIssue())
                .dateOfIssue(from.getDateOfIssue())
                .transaction(from.getTransaction())
                .agreement(from.getAgreements())
                .worker(from.getWorker())
                .build();
    }

    @Override
    public Invoice reverse(InvoiceDto to) {
        return Invoice.builder()
                .id(to.getId())
                .invoiceNumber(to.getInvoiceNumber())
                .price(to.getPrice())
                .placeOfIssue(to.getPlaceOfIssue())
                .dateOfIssue(to.getDateOfIssue())
                .transaction(to.getTransaction())
                .agreements(to.getAgreement())
                .worker(to.getWorker())
                .build();
    }
}
