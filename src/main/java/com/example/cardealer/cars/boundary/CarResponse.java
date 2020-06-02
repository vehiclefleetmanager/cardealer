package com.example.cardealer.cars.boundary;

import com.example.cardealer.cars.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarResponse {
    Long id;
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
    String status;

    public static CarResponse from(Car car) {
        return new CarResponse(
                car.getId(),
                car.getBodyNumber(),
                car.getProductionYear(),
                car.getMark(),
                car.getModel(),
                car.getOcNumber(),
                car.getFuelType().getTypeName(),
                car.getDistance(),
                car.getBodyType().getTypeName(),
                car.getCapacityEngine(),
                car.getPowerEngine(),
                car.getTransmission().getTypeName(),
                car.getDescription(),
                car.getPrice().intValue(),
                car.getStatus().name()
        );
    }
}
