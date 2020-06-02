package com.example.cardealer.events.boundary;

import com.example.cardealer.events.entity.TestDrive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestDriveResponse {
    Long id;
    String date;
    String time;
    String customerFirstName;
    String customerLastName;
    String carMark;
    String carModel;
    String carBodyNumber;
    String status;

    public static TestDriveResponse from(TestDrive testDrive) {
        return new TestDriveResponse(
                testDrive.getId(),
                testDrive.getDateOfTestDrive().toString(),
                testDrive.getTimeOfTestDrive().toString(),
                testDrive.getUser().getFirstName(),
                testDrive.getUser().getLastName(),
                testDrive.getCar().getMark(),
                testDrive.getCar().getModel(),
                testDrive.getCar().getBodyNumber(),
                testDrive.getStatus().name()
        );
    }
}
