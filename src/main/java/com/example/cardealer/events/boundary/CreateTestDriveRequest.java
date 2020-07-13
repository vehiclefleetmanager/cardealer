package com.example.cardealer.events.boundary;

import lombok.Data;

@Data
public class CreateTestDriveRequest {
    Long carId;
    Long userId;
    String date;
    String time;
}
