package com.example.cardealer.model;

import javax.persistence.*;

/**
 * FAKTURA
 */

@Entity
@Table(name = "invoices")
public class Invoice {
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

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Agreement getAgreements() {
        return agreements;
    }

    public void setAgreements(Agreement agreements) {
        this.agreements = agreements;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
