package com.example.cardealer.cars.boundary;

import lombok.Data;

@Data
public class CreateTestDriveRequest {
    Long carId;
    Long userId;
    String date;
    String time;
}
