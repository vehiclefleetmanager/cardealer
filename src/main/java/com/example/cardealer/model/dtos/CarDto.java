package com.example.cardealer.model.dtos;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Owner;
import com.example.cardealer.model.enums.Transaction;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto {
    private Integer id;
    private String bodyNumber;
    private Integer productionYear;
    private String mark;
    private String model;
    private String ocNumber;
    private String regNumber;
    private Car.FuelType fuelType;
    private Car.Status status;
    private Integer distance;
    private Integer capacityEngine;
    private Integer powerEngine;
    private Car.Transmission transmission;
    private String description;
    private Integer testDrive;
    private BigDecimal price;
    private Owner owner;


   /* private Integer customerId;
    private String lastName;
    private String firstName;
    private String address;
    private Long tin;
    private Long pesel;
    private String phoneNumber;

    private String eventDate;

    private Transaction transaction;*/

}
