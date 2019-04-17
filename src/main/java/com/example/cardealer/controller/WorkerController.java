package com.example.cardealer.controller;

import com.example.cardealer.dto.CarDto;
import com.example.cardealer.dto.EventDto;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.service.AgreementService;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.CustomerService;
import com.example.cardealer.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    private final CustomerService customerService;
    private final CarService carService;
    private final EventService eventService;
    private final AgreementService agreementService;

    @Autowired
    public WorkerController(CustomerService customerService,
                            CarService carService,
                            EventService eventService,
                            AgreementService agreementService) {
        this.customerService = customerService;
        this.carService = carService;
        this.eventService = eventService;
        this.agreementService = agreementService;
    }


    @GetMapping("/customers-list")
    public String showCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "/worker/customers-list";
    }

    @GetMapping("/{id}/customer-edit")
    public String editCustomer(@PathVariable("id") Integer id, Model model) {
        Customer databaseCustomer = customerService.getCustomer(id);
        model.addAttribute("customer", databaseCustomer);
        return "/worker/customer-edit";
    }

    @GetMapping("/customer-add")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "/worker/customer-edit";
    }

    @PostMapping("/customer-save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/worker/customers-list";
    }

    /*@GetMapping("/{id}/delete-customer")
    public String deleteCustomer(@PathVariable("id") Integer id, Customer customer) {
        id = customer.getCustomerNumber();
        customerService.deleteById(id);
        return "redirect:/worker/customers-list";
    }*/


    @GetMapping("/cars-list")
    public String showCars(@ModelAttribute("carDto") CarDto carDto, Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "/worker/cars-list";
    }

    /*Filtrowanie list po typie transakcji pojazdu*/
    @GetMapping("/cars-list-filter")
    public String showCarsFilter(@ModelAttribute("carDto") CarDto carDto, @RequestParam("transaction") Transaction transaction, Model model) {
        List<Car> carByTransactionLike = carService.findCarByTransactionLike(transaction);
        model.addAttribute("cars", carByTransactionLike);
        return "/worker/cars-list";
    }

    @GetMapping("{id}/car-edit")
    public String editCar(@PathVariable("id") Integer id, Model model) {
        Car databaseCar = carService.getCar(id);
        model.addAttribute("car", databaseCar);
        return "/worker/car-edit";
    }

    @GetMapping("/car-add")
    public String addCar(Model model) {
        model.addAttribute("car", new Car());
        return "/worker/car-edit";
    }

    @PostMapping("/car-save")
    public String saveCar(@ModelAttribute("car") Car car) {
        carService.save(car);
        return "redirect:/worker/cars-list";
    }

    @GetMapping("{id}/car-delete")
    public String deleteCar(@PathVariable("id") Integer id, Car car) {
        id = car.getId();
        carService.deleteById(id);
        return "redirect:/worker/cars-list";
    }

    @GetMapping("{id}/car-details")
    public String showDetailsCar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("car", carService.getCar(id));
        return "/worker/car-details";
    }


    @GetMapping("/show/car?transaction=WAITING")
    public String showCarsWaiting(@ModelAttribute("carDto") CarDto carDto, @RequestParam("transaction") Transaction transaction, Model model) {
        model.addAttribute("cars", carService.findByWaitingLike(transaction));
        return "/worker/cars-waiting";
    }





    /*przeniesienie auta:
     - z listy oczekujących na akceptacje przez serwis
     - na liste wystawionych do sprzedaży*/
   /* @GetMapping("/{id}/car-accept")
    public String acceptCar(@PathVariable("id") Integer id, Car car, Customer customer) {
        *//**wyciągamy z bazy zdarzenie o przekazanym id samochodu*//*

        Event eventByCarId = eventService.findEventByCarId(id);

        *//**wyciągamy z bazy samochód o przekazanym id samochodu*//*
        eventByCarId.setCar(carService.getCar(id));

        *//**wyciągamy z bazy klienta o przekazanym id samochodu*//*
        eventByCarId.setCustomer(eventService.findCustomerByCarId(id));

        *//**zmieniamy typ transakcji oraz date zdarzenia*//*
        eventByCarId.setTransaction(Transaction.RENOUNCEMENT);
        eventByCarId.setEventDate(new LacalDate());

        *//**spisujemy umowe odstąpienia*//*
        Agreement agreement = new Agreement();
        agreement.setCar(eventByCarId.getCar());
        agreement.setCustomer(eventByCarId.getCustomer());
        agreement.setTransaction(eventByCarId.getTransaction());

        *//**dodajemy umowe do auta i klienta*//*
        customer.addAgreement(agreement);

        *//**dodajemy zdrzenie do auta i klienta*//*
        car.addEvent(eventByCarId);
        customer.addEvent(eventByCarId);

        */

    /**
     * zapisujemy zmiany
     *//*
        eventService.save(eventByCarId);
        agreementService.save(agreement);
        return "redirect:/worker/cars-list";
    }*/

    @GetMapping("/orders-list")
    public String showOrders(Model eventModel,
                             Model carModel,
                             Model customerModel) {
        List<Event> events = eventService.findEventByTesting(Transaction.TESTING);
        eventModel.addAttribute("events", events);
        carModel.addAttribute("car");
        customerModel.addAttribute("customer");
        return "/worker/orders-list";
    }

    @GetMapping("{id}/orders")
    public String showOrders(@PathVariable("id") Integer id,
                             @ModelAttribute("eventDto") EventDto eventDto,
                             Model model) {
        Event databaseEvent = eventService.getEvent(id);
        model.addAttribute("eventDto", databaseEvent);
        return "orders-list";
    }

}
