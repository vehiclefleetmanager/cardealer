package com.example.cardealer.model;

import com.example.cardealer.model.enums.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UMOWA
 */

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

    public Agreement() {
    }

    public Agreement(Transaction transaction, Date dateTransaction) {
        this.transaction = transaction;

    }

    public Agreement(Transaction transaction,
                     BigDecimal value, Date dateTransaction, Set<Invoice> invoices) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void addInvoice(Invoice invoice) {
        if (invoices == null) {
            invoices = new HashSet<>();
        }
        invoices.add(invoice);
    }

}
