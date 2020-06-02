package com.example.cardealer.events.boundary;

import lombok.Data;

@Data
public class CreateRepairRequest {
    String repairAmount;
    String repairDescription;
}
