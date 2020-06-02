package com.example.cardealer.documents.entiity;

import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.utils.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class describes the Invoices in the application.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice extends Document {

    private String invoiceNumber;

    private BigDecimal invoiceAmount;
    @OneToOne
    private Agreement agreement;
    @OneToOne
    private Employee employee;

    public Invoice(Agreement agreement, Transaction transaction, LocalDate date, BigDecimal saleAmount) {
        this.agreement = agreement;
        this.setTransaction(transaction);
        this.setCreatedAt(date);
        this.setInvoiceAmount(saleAmount);
    }
}
