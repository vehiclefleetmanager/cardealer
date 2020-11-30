package com.example.cardealer.documents.entiity;

import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.events.entity.Event;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
public class Agreement extends Document {

    private String agreementNumber;

    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private BigDecimal agreementAmount;

    @Transient
    private Event event;


    public Agreement(Customer customer, Event event, LocalDate date, String content) {
        super();
        setCustomer(customer);
        setEvent(event);
        setCreatedAt(date);
        this.content = content;
    }
}