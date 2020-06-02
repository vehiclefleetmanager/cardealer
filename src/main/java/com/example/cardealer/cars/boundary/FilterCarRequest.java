package com.example.cardealer.cars.boundary;

import lombok.Data;

@Data
public class FilterCarRequest {
    String mark;
    String maxYear;
    String maxPrice;
}
