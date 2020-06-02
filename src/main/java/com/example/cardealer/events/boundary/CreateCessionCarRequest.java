package com.example.cardealer.events.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateCessionCarRequest {
    Long carId;
    BigDecimal price;
}
