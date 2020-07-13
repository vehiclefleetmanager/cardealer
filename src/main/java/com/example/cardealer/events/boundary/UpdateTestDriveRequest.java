package com.example.cardealer.events.boundary;

import lombok.Data;

@Data
public class UpdateTestDriveRequest {
    Long id;
    Long carId;
    Long userId;
    String date;
    String time;
}
