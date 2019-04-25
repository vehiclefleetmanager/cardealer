package com.example.cardealer.controller;

import com.example.cardealer.model.dtos.OwnerDto;
import com.example.cardealer.service.OwnerService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@Data
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @PutMapping("/owner")
    public void updateOwner(@RequestBody OwnerDto ownerDto) {
        ownerService.updateOwner(ownerDto);
    }
}
