package com.example.cardealer.model;

import com.example.cardealer.model.enums.FuelType;
import com.example.cardealer.model.enums.Transmission;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SAMOCHÓD
 */

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String bodyNumber;

    @Column
    private Integer productionYear;

    @Column
    private String mark;

    @Column
    private String model;

    @Column
    private String ocNumber;

    @Column(unique = true)
    private String regNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column
    private Integer distance;

    @Column
    private Integer capacityEngine;

    @Column
    private Integer powerEngine;

    @Column
    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Column
    private String description;

    @Column
    private Integer testDrive;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "car")
    private Set<Agreement> agreements;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cars_customers",
        joinColumns = {
            @JoinColumn(name = "car_id")
        },
    inverseJoinColumns = {
            @JoinColumn(name = "customer_id" )
    })
    private Set<Customer> customers;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Set<Event> events;


    public Car() {
    }

    public Car(
            String bodyNumber,
            Integer productionYear, String mark,
            String model, String ocNumber, String regNumber,
            FuelType fuelType, Integer distance,
            Integer capacityEngine, Integer powerEngine,
            Transmission transmission,
            String description, BigDecimal price, Integer testDrive) {
        this.bodyNumber = bodyNumber;
        this.productionYear = productionYear;
        this.mark = mark;
        this.model = model;
        this.ocNumber = ocNumber;
        this.regNumber = regNumber;
        this.fuelType = fuelType;
        this.distance = distance;
        this.capacityEngine = capacityEngine;
        this.powerEngine = powerEngine;
        this.transmission = transmission;
        this.description = description;
        this.price = price;
        this.testDrive = testDrive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBodyNumber() {
        return bodyNumber;
    }

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOcNumber() {
        return ocNumber;
    }

    public void setOcNumber(String ocNumber) {
        this.ocNumber = ocNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getCapacityEngine() {
        return capacityEngine;
    }

    public void setCapacityEngine(Integer capacityEngine) {
        this.capacityEngine = capacityEngine;
    }

    public Integer getPowerEngine() {
        return powerEngine;
    }

    public void setPowerEngine(Integer powerEngine) {
        this.powerEngine = powerEngine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTestDrive() {
        return testDrive;
    }

    public void setTestDrive(Integer testDrive) {
        this.testDrive = testDrive;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public void addCustomer(Customer customer) {
        if (customers == null) {
            customers = new HashSet<>();
        }
        customers.add(customer);
    }

    public void addAgreement(Agreement agreement) {
        if (agreements == null) {
            agreements = new HashSet<>();
        }
        agreements.add(agreement);
    }

    public void addEvent(Event event) {
        if (events == null) {
            events = new HashSet<>();
        }
        events.add(event);
    }
}
