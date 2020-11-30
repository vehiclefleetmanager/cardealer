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
 * Purchase
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "purchases")
public class Purchase extends Event {

    private LocalDate eventDate;
    private BigDecimal purchaseAmount;

    public Purchase(Car updateCar, Customer oldOwner, LocalDate date,
                    BigDecimal purchasePrice, Agreement agreement) {
        setCustomer(oldOwner);
        setCar(updateCar);
        this.eventDate = date;
        this.purchaseAmount = purchasePrice;
        setAgreement(agreement);
    }
}
