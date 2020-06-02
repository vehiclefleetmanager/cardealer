package com.example.cardealer.events.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateCarSaleRequest {
    Long carId;
    Long newOwnerId;
    BigDecimal price;
}
