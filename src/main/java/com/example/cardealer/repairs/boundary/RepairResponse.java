package com.example.cardealer.repairs.boundary;

import com.example.cardealer.repairs.entity.Repair;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepairResponse {
    Long id;
    String repairDate;
    String repairAmount;
    String carBodyNumber;

    public static RepairResponse from(Repair repair) {
        return new RepairResponse(
                repair.getId(),
                repair.getRepairDate().toString(),
                repair.getRepairAmount().toString(),
                repair.getCar().getBodyNumber()
        );
    }
}
