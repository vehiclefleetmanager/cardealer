package com.example.cardealer.controller;

import com.example.cardealer.model.Car.Status;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.OwnerDto;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class OwnerController {
    private final OwnerService ownerService;
    private final CarService carService;

    public OwnerController(OwnerService ownerService,
                           CarService carService) {
        this.ownerService = ownerService;
        this.carService = carService;
    }


    @PutMapping("/owner")
    public void updateOwner(@RequestBody OwnerDto ownerDto) {
        ownerService.updateOwner(ownerDto);
    }


    @GetMapping("/owner/{ownerId}/cars")
    public String getPageWhereIsListCarsOfOwner(Model model, @PathVariable Integer ownerId) {
        model.addAttribute("owner", ownerService.getOwnerById(ownerId));
        model.addAttribute("cars", carService.getCarsByOwnersIds(ownerId));
        return "cars-list";
    }

    @GetMapping("/owner/{carId}/details")
    public String getPageWhereIsDetailsCar(Model model, @PathVariable Integer carId) {
        model.addAttribute("car", carService.getCar(carId));
        return "details";
    }

    @GetMapping("/owner/{ownerId}/add-car")
    public String getPageToAddCarByOwnerId(@PathVariable Integer ownerId, Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(ownerId));
        model.addAttribute("carDto", new CarDto());
        return "add-car";
    }

    @PostMapping("/owner/{objectId}/addCar")
    public String addCarByOwnerId(@ModelAttribute("carDto") CarDto carDto, @PathVariable Integer objectId) {
        carDto.setOwnerId(ownerService.getOwnerById(objectId).getOwnerId());
        carDto.setStatus(Status.WAIT);
        carDto.setTestDrive(0);
        carService.addCar(carDto);
        return "/owner/" + objectId + "/cars";
    }
}
