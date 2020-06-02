package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.entiity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("select i from Invoice i where i.createdAt between ?1 and ?2")
    Collection<Invoice> findInvoiceByDateScope(LocalDate from, LocalDate to);

}
