package com.example.cardealer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * KLIENT
 */

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNumber;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String address;

    @Column(unique = true)
    private Long tin;

    @Column(unique = true)
    private Long pesel;

    @JsonIgnore
    @ManyToMany(mappedBy = "customers", cascade = CascadeType.ALL)
    private Set<Car> cars;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Agreement> agreements;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Event> events;


    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, Long tin, Long pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.tin = tin;
        this.pesel = pesel;
    }

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTin() {
        return tin;
    }

    public void setTin(Long tin) {
        this.tin = tin;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Set<Agreement> getAgreements() {
        return agreements;
    }

    public void setAgreements(Set<Agreement> agreements) {
        this.agreements = agreements;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
    }

    public void addEvents(Event event) {
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
