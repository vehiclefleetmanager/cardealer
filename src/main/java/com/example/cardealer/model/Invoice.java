package com.example.cardealer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * This class describes the Invoices in the application.
 */

@Builder
@AllArgsConstructor
@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    private static Integer number = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "agreement_id")
    private Agreement agreements;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;


    public Invoice() {
        String actualYear = String.valueOf(LocalDate.now().getYear());
        String actualMonth = String.valueOf(LocalDate.now().getMonth());
        this.invoiceNumber = number.toString() +
                "/" + actualMonth + "/" + actualYear;
        number++;
    }

}
