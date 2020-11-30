package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.entiity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("select i from Invoice i where i.createdAt between ?1 and ?2")
    Collection<Invoice> findInvoiceByDateScope(LocalDate from, LocalDate to);

    @Query("select i from Invoice i where i.customer.id = ?1")
    Page<Invoice> findAllInvoicesOfUser(Long userId, Pageable pageable);

    @Query("select i from Invoice i where i.customer.id = ?1")
    List<Invoice> findAllInvoicesOfCustomer(Long id);
}
