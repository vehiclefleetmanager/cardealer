package com.example.cardealer.events.boundary;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCessionRequest {
    /*Car*/
    String bodyNumber;
    Integer productionYear;
    String mark;
    String model;
    String ocNumber;
    String fuelType;
    Integer distance;
    String bodyType;
    Integer capacityEngine;
    Integer powerEngine;
    String transmission;
    String description;
    BigDecimal price;
    /*Owner*/
    String firstName;
    String lastName;
    String address;
    String phoneNumber;
    String tin;
    String pesel;
    String idNumber;
    String email;
}
