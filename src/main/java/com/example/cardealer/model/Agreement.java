package com.example.cardealer.model;

import com.example.cardealer.model.enums.Transaction;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Agreements in the application.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @Enumerated(EnumType.STRING)
    private Transaction transaction;

    @OneToMany(mappedBy = "agreements", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;


    private Set<Invoice> getInvoices() {
        if (invoices == null) {
            invoices = new HashSet<>();
        }
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        getInvoices().add(invoice);
        invoice.setId(this.getId());
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", customer=" + customer +
                ", car=" + car +
                ", transaction=" + transaction +
                '}';
    }
}
