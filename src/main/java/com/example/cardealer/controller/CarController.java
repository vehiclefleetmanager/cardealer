
package com.example.cardealer.controller;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.service.CarService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Data
@RestController
@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<CarDto> getCars() {
        return carService.getCarsDto();
    }

    /*metodę trzeba zabezpieczyć przed nullem*/
    @GetMapping("/cars/{reg_number}")
    public CarDto getCarByRegNumber(@RequestParam(value = "reg_number", required = false)
                                     @PathVariable String reg_number) {
        return carService.getCarFindByRegNumber(reg_number);
    }
    /*metodę trzeba zabezpieczyć przed nullem*/
    @GetMapping("/cars/{body_number}")
    public CarDto getCarByBodyNumber(@RequestParam(value = "body_number", required = false)
                                    @PathVariable String body_number) {
        return carService.getCarFindByRegNumber(body_number);
    }

    @PostMapping("/cars")
    public Car addCar(@RequestBody CarDto carDto) {
        return carService.addCar(carDto);
    }

    @PutMapping("/cars")
    public void updateCar(@RequestBody CarDto carDto) {
        carService.updateCar(carDto);
    }

    @DeleteMapping("/cars/{reg_number}")
    public void deleteCar(@PathVariable String reg_number) {
        carService.deleteCarByRegNumber(reg_number);
    }
}
