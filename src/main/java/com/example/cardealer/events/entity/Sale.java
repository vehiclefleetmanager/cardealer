package com.example.cardealer.events.entity;


import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.documents.entiity.Agreement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * SPRZEDAŻ
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sales")
public class Sale extends Event {

    private LocalDate eventDate;
    private BigDecimal saleAmount;

    public Sale(Car updateCar, Customer newOwner, LocalDate date,
                Agreement agreement, BigDecimal price) {
        setCustomer(newOwner);
        setCar(updateCar);
        this.eventDate = date;
        this.saleAmount = price;
        setAgreement(agreement);

    }
}
