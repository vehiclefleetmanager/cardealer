package com.example.cardealer.repairs.boundary;

import lombok.Data;

@Data
public class FilterRepairRequest {

    String from;
    String to;
    String vinNumber;
    String repairAmount;
}
