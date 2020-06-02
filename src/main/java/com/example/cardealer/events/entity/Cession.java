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
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cessions")
public class Cession extends Event {

    private LocalDate cessionDate;

    public Cession(Car updateCar, LocalDate date, Customer carOwner, Agreement agreement, Employee employee) {
        setCar(updateCar);
        setCustomer(carOwner);
        setAgreement(agreement);
        setEmployee(employee);
        this.cessionDate = date;
    }
}
