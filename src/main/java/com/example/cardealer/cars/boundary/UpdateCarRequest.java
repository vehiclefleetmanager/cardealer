package com.example.cardealer.cars.boundary;

import lombok.Data;

@Data
public class UpdateCarRequest {
    Long carId;
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
    Integer price;
}
