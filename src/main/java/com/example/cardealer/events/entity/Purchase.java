package com.example.cardealer.events.entity;

import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.documents.entiity.Agreement;
import com.example.cardealer.employees.entity.Employee;
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

    private LocalDate purchaseDate;
    private BigDecimal purchaseAmount;

    public Purchase(Car updateCar, Customer oldOwner, LocalDate date,
                    Agreement newAgreement, BigDecimal purchasePrice,
                    Employee employee) {
        setCar(updateCar);
        setCustomer(oldOwner);
        setAgreement(newAgreement);
        setEmployee(employee);
        this.purchaseDate = date;
        this.purchaseAmount = purchasePrice;
    }
}
