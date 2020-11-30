package com.example.cardealer.events.entity;


import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.customers.entity.Customer;
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
@Table(name = "cessions")
public class Cession extends Event {

    private LocalDate eventDate;

    public Cession(Car updateCar, Customer carOwner, LocalDate date) {
        super();
        setCustomer(carOwner);
        setCar(updateCar);
        this.setEventDate(date);
    }
}
