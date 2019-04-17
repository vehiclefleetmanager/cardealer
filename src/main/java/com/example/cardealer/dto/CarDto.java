package com.example.cardealer.dto;


import com.example.cardealer.model.enums.FuelType;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.model.enums.Transmission;

import java.math.BigDecimal;

public class CarDto {
    private Integer id;
    private String bodyNumber;
    private Integer productionYear;
    private String mark;
    private String model;
    private String ocNumber;
    private String regNumber;
    private FuelType fuelType;
    private Integer distance;
    private Integer capacityEngine;
    private Integer powerEngine;
    private Transmission transmission;
    private String description;
    private Integer testDrive;
    private BigDecimal price;

    private Integer customerId;
    private String lastName;
    private String firstName;
    private String address;
    private Long tin;
    private Long pesel;
    private String phoneNumber;

    private String eventDate;

    private Transaction transaction;


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    /*public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }*/

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
