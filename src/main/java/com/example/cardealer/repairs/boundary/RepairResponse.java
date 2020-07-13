package com.example.cardealer.repairs.boundary;

import com.example.cardealer.repairs.entity.Part;
import com.example.cardealer.repairs.entity.Repair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RepairResponse {
    Long id;
    String repairDate;
    String repairAmount;
    String repairInvoiceNumber;
    String repairDescription;
    String carBodyNumber;
    String carProductionYear;
    String carDistance;
    String carMark;
    String carModel;
    String mechanicFName;
    String mechanicLName;
    Collection<String> partsName;


    public static RepairResponse from(Repair repair) {
        return new RepairResponse(
                repair.getId(),
                repair.getRepairDate().toString(),
                repair.getRepairAmount().toString(),
                repair.getRepairInvoice().getInvoiceNumber(),
                repair.getRepairDescription(),
                repair.getCar().getBodyNumber(),
                repair.getCar().getProductionYear().toString(),
                repair.getCar().getDistance().toString(),
                repair.getCar().getMark(),
                repair.getCar().getModel(),
                repair.getEmployee().getFirstName(),
                repair.getEmployee().getLastName(),
                repair.getParts().stream().map(Part::getPartName).collect(Collectors.toList()
                ));
    }
}
