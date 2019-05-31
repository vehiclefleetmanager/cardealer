package com.example.cardealer.service;

import com.example.cardealer.mappers.InvoiceMapper;
import com.example.cardealer.model.Invoice;
import com.example.cardealer.model.dtos.InvoiceDto;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;


    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public List<InvoiceDto> getInvoiceDtoBySold() {
        return invoiceRepository
                .findInvoiceByTransaction(Transaction.SALE)
                .stream()
                .map(invoiceMapper::map)
                .collect(Collectors.toList());
    }

    public List<InvoiceDto> getInvoiceDtoByBought() {
        return invoiceRepository
                .findInvoiceByTransaction(Transaction.PURCHASE)
                .stream()
                .map(invoiceMapper::map)
                .collect(Collectors.toList());
    }
}
