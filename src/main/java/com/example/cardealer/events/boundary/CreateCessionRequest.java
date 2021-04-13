package com.example.cardealer.events.boundary;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class CreateCessionRequest {
    /*Car*/
    @NotEmpty
    @NotBlank
    String bodyNumber;

    @Positive
    Integer productionYear;

    @NotEmpty
    @NotBlank
    String mark;

    @NotEmpty
    @NotBlank
    String model;

    @NotEmpty
    @NotBlank
    String ocNumber;

    @NotNull
    String fuelType;

    @NotNull
    @Positive
    Integer distance;

    @NotNull
    String bodyType;

    @NotNull
    @Positive
    Integer capacityEngine;

    @NotNull
    @Positive
    Integer powerEngine;

    @NotNull
    String transmission;

    @NotEmpty
    @NotBlank
    String description;

    @NotNull
    @DecimalMin("5000.00")
    BigDecimal price;

    /*Owner*/
    @NotEmpty
    @NotBlank
    String firstName;

    @NotEmpty
    @NotBlank
    String lastName;

    @NotEmpty
    @NotBlank
    String address;

    @NotEmpty
    @NotBlank
    String phoneNumber;

    @NotEmpty
    @NotBlank
    String tin;

    @NotEmpty
    @NotBlank
    String pesel;

    @NotEmpty
    @NotBlank
    String idNumber;

    @Email
    String email;
}
