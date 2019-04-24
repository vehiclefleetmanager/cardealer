package com.example.cardealer.mappers;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.dtos.CarDto;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements Mapper<Car, CarDto> {
    @Override
    public CarDto map(Car from) {

        return CarDto.builder()
                .id(from.getId())
                .bodyNumber(from.getBodyNumber())
                .capacityEngine(from.getCapacityEngine())
                .description(from.getDescription())
                .distance(from.getDistance())
                .fuelType(from.getFuelType())
                .mark(from.getMark())
                .model(from.getModel())
                .ocNumber(from.getOcNumber())
                .powerEngine(from.getPowerEngine())
                .price(from.getPrice())
                .productionYear(from.getProductionYear())
                .regNumber(from.getRegNumber())
                .status(from.getStatus())
                .testDrive(from.getTestDrive())
                .transmission(from.getTransmission())
                .ownerId(from.getOwnerId())
                .build();
    }

    @Override
    public Car reverse(CarDto to) {
        return Car.builder()
                .id(to.getId())
                .bodyNumber(to.getBodyNumber())
                .capacityEngine(to.getCapacityEngine())
                .description(to.getDescription())
                .distance(to.getDistance())
                .fuelType(to.getFuelType())
                .mark(to.getMark())
                .model(to.getModel())
                .ocNumber(to.getOcNumber())
                .powerEngine(to.getPowerEngine())
                .price(to.getPrice())
                .productionYear(to.getProductionYear())
                .regNumber(to.getRegNumber())
                .status(to.getStatus())
                .testDrive(to.getTestDrive())
                .transmission(to.getTransmission())
                .ownerId(to.getOwnerId())
                .build();
    }
}
