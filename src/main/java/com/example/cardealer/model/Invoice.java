package com.example.cardealer.model;

import com.example.cardealer.model.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * This class describes the Invoices in the application.
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String invoiceNumber;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "place_of_issue")
    private String placeOfIssue;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Enumerated(EnumType.STRING)
    private Transaction transaction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agreement_id", referencedColumnName = "id")
    private Agreement agreements;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private Worker worker;


    /*public Invoice() {
        int invoiceNumber = id;
        String actualYear = String.valueOf(LocalDate.now().getYear());
        String actualMonth = String.valueOf(LocalDate.now().getMonth().ordinal()+1);
        this.invoiceNumber = invoiceNumber +
                "/" + actualMonth + "/" + actualYear;
    }*/

}
