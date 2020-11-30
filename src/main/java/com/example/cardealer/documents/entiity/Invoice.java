package com.example.cardealer.documents.entiity;

import com.example.cardealer.employees.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private BigDecimal invoiceAmount;

    public Invoice(Agreement agreement, Employee employee) {
        setAgreement(agreement);
        setEmployee(employee);
    }
}
