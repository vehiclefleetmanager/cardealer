package com.example.cardealer.repository;

import com.example.cardealer.model.Invoice;
import com.example.cardealer.model.dtos.InvoiceDto;
import com.example.cardealer.model.enums.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select i from Invoice i where i.transaction = ?1")
    List<Invoice> findInvoiceByTransaction(Transaction transaction);
}
