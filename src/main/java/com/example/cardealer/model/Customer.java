package com.example.cardealer.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Customers in the application.
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends Person {

    @Column(name = "tin", unique = true)
    private String tin;

    @Column(name = "pesel", unique = true)
    private String pesel;


    @ManyToMany(mappedBy = "customers", cascade = CascadeType.ALL)
    private Set<Car> cars;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Agreement> agreements;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Event> events;


    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
    }

    public void addEvent(Event event) {
        if (events == null) {
            events = new HashSet<>();
        }
        events.add(event);
    }

    public void addAgreement(Agreement agreement) {
        if (agreements == null) {
            agreements = new HashSet<>();
        }
        agreements.add(agreement);
    }
}
