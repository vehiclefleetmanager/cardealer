package com.example.cardealer.documents.control;

import com.example.cardealer.documents.boundary.InvoiceRepository;
import com.example.cardealer.documents.entiity.Invoice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Page<Invoice> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Page<Invoice> findAllInvoicesOfUser(Long userId, Pageable pageable) {
        return invoiceRepository.findAllInvoicesOfUser(userId, pageable);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.getOne(id);
    }
}
