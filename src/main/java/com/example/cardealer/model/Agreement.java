package com.example.cardealer.model;

import com.example.cardealer.model.enums.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
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
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;
    @Enumerated
    private Transaction transactionType;
    @OneToMany(mappedBy = "agreement")
    private Set<Invoice> invoices;

    public Agreement() {
    }

    public Agreement(Transaction transaction,
                      BigDecimal value, Date dateTransaction){

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
}
