package com.example.cardealer.controller;

import com.example.cardealer.dto.CarDto;
import com.example.cardealer.dto.EventDto;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public String showFormToSale(Model model) {
        model.addAttribute("carDto", new CarDto());
        return "/customer/sale";
    }

    @GetMapping("/{id}/details")
    public String showDetailsCar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("car", carService.getCar(id));
        return "/customer/details";
    }

    @GetMapping("/{id}/customer-add")
    public String addCustomer(@PathVariable("id") Integer id, Model customerModel, Model carModel, Model eventModel) {
        Car databaseCar = carService.getCar(id);
        carModel.addAttribute("carDto", databaseCar);
        customerModel.addAttribute("customerDto", new Customer());
        eventModel.addAttribute("eventDto", new Event());
        return "/customer/customer-add";
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
                carDto.getPrice(),
                carDto.getTestDrive());

        Event event = new Event(Transaction.WAITING,
                new Date(),
                customer,
                car);

        customer.addCar(car);
        car.addCustomer(customer);
        car.addEvent(event);
        customer.addEvent(event);

        carService.save(car);
        eventService.save(event);

        return "redirect:/";
    }

    @PostMapping("/add-meet")
    public String addMeetToTestDrive(@ModelAttribute("carDto") CarDto carDto, EventDto eventDto) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(eventDto.getEventDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Car databaseCar = carService.getCar(carDto.getId());
        /*aktualizacja stanu jazd testowych*/
        carService.updateTestDrive(databaseCar);

        Customer customer = new Customer();
        customer.setFirstName(carDto.getFirstName());
        customer.setLastName(carDto.getLastName());
        customer.setAddress(carDto.getAddress());
        customer.setPesel(carDto.getPesel());
        customer.setTin(carDto.getTin());

        Event event = new Event();
        event.setTransaction(Transaction.TESTING);
        event.setCustomer(customer);
        event.setCar(databaseCar);
        event.setEventDate(parse);

        customer.addEvent(event);

        eventService.save(event);

        return "redirect:/";
    }


}
