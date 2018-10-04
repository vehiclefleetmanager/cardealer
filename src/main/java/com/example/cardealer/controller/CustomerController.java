package com.example.cardealer.controller;

import com.example.cardealer.dto.CarDto;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CarService carService;
    private final EventService eventService;

    @Autowired
    public CustomerController(CarService carService,
                              EventService eventService) {
        this.carService = carService;
        this.eventService = eventService;
    }


    @GetMapping("/sale")
    public String showFormToSale(@ModelAttribute("carDto") CarDto carDto) {

        return "/customer/sale";
    }

    @GetMapping("/{id}/details")
    public String showDetailsCar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("car", carService.getCar(id));
        return "/customer/details";
    }

    @PostMapping("/new/save")
    public String saleCar(@ModelAttribute("carDto") CarDto carDto) {

        Customer customer = new Customer(
                carDto.getFirstName(),
                carDto.getLastName(),
                carDto.getAddress(),
                carDto.getTin(),
                carDto.getPesel());

        Car car = new Car(
                carDto.getBodyNumber(),
                carDto.getProductionYear(),
                carDto.getMark(),
                carDto.getModel(),
                carDto.getOcNumber(),
                carDto.getRegNumber(),
                carDto.getFuelType(),
                carDto.getDistance(),
                carDto.getCapacityEngine(),
                carDto.getPowerEngine(),
                carDto.getTransmission(),
                carDto.getDescription(),
                carDto.getPrice());

        Event event = new Event(Transaction.SALE,
                new Date(),
                customer,
                car);

        customer.addCar(car);
        car.addCustomer(customer);

        carService.save(car);
        eventService.save(event);

        return "redirect:/";
    }


}
