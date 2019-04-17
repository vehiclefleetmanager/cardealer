package com.example.cardealer.model;

import com.example.cardealer.model.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Agreements in the application.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_id")
    private Car car;

    @Enumerated(EnumType.STRING)
    private Transaction transaction;

    @OneToMany(mappedBy = "agreements")
    private Set<Invoice> invoices;


    public Set<Invoice> getIvoices() {
        if (invoices == null) {
            invoices = new HashSet<>();
        }
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        getInvoices().add(invoice);
        invoice.setId(this.getId());
    }

}
