package com.example.cardealer.events.entity;


import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.documents.entiity.Agreement;
import com.example.cardealer.employees.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private LocalDate saleDate;
    private BigDecimal saleAmount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Sale(Car updateCar, Customer newOwner, LocalDate date,
                Agreement agreement, BigDecimal price,
                Employee employee) {
        setCar(updateCar);
        setCustomer(newOwner);
        setAgreement(agreement);
        setEmployee(employee);
        this.saleDate = date;
        this.saleAmount = price;

    }
}
