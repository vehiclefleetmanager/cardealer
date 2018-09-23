package com.example.cardealer.model;

import com.example.cardealer.model.enums.FuelType;

import javax.persistence.*;
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
    private String OcNumber;
    @Column(unique = true)
    private String regNumber;
    @Column
    private Enum<FuelType> fuelType;
    @Column
    private Integer distance;
    @Column
    private Integer capacityEngine;
    @Column
    private Integer powerEngine;
    @Column
    private String transmission;
    @Column
    private String description;
    @Column
    private Integer testDrive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cars_customers",
        joinColumns = {
            @JoinColumn(name = "car_id")
        },
    inverseJoinColumns = {
            @JoinColumn(name = "customer_id" )
    })
    private Set<Customer> customerSet;

    public Car() {

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
        return OcNumber;
    }

    public void setOcNumber(String ocNumber) {
        OcNumber = ocNumber;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Enum<FuelType> getFuelType() {
        return fuelType;
    }

    public void setFuelType(Enum<FuelType> fuelType) {
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

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
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

    public Set<Customer> getCustomerSet() {
        return customerSet;
    }

    public void setCustomerSet(Set<Customer> customerSet) {
        this.customerSet = customerSet;
    }
}
