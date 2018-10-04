package com.example.cardealer.controller;

import com.example.cardealer.dto.CarDto;
import com.example.cardealer.model.Agreement;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.CustomerService;
import com.example.cardealer.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    private final CustomerService customerService;
    private final CarService carService;
    private final EventService eventService;

    @Autowired
    public WorkerController(CustomerService customerService,
                            CarService carService,
                            EventService eventService) {
        this.customerService = customerService;
        this.carService = carService;
        this.eventService = eventService;
    }


    @GetMapping("/customers-list")
    public String showCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "/worker/customers-list";
    }


    @GetMapping("/cars-list")
    public String show(@ModelAttribute("carDto") CarDto carDto, Model model) {
        model.addAttribute("cars", carService.findAll());
        return "/worker/cars-list";
    }

    @GetMapping("/{id}/customer-edit")
    public String showEditCustomer(@PathVariable("id") Integer id, Model model) {
        Customer databaseCustomer = customerService.findById(id);
        model.addAttribute("customer", databaseCustomer);
        return "/worker/customer-edit";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/worker/customer-list";
    }

    @GetMapping("/show/car")
    public String showCar(@ModelAttribute("carDto") CarDto carDto, Model model) {
        model.addAttribute("cars", carService.findCarByTransactionLike(carDto.getTransaction()));
        return "/worker/cars-list";
    }

    @GetMapping("/{id}/accept")
    public String acceptCar(@PathVariable("id") @ModelAttribute("carDto") CarDto carDto, Integer id) {

        /**przeniesienie auta z listy oczekujących na akceptacje przez serwis
         * na liste wystawionych do sprzedaży*/

        /**wyciągamy z bazy samochód o przekazanym id*/
        Car car = carService.getCar(id);

        /**poprzez id samochodu wyciągamy z bazy klienta*/
        Customer customer = customerService.getCustomer(car.getId());

        /**poprzez id samochodu wyciągamy z bazy zdarzenie */
        Event event = eventService.getEvent(car.getId());

        /**zmieniamy typ transakcji oraz date zdarzenia*/
        event.setTransaction(Transaction.RENOUNCEMENT);
        event.setEventDate(new Date());
        /*event.setWorker()*/


        /**spisujemy umowe odstąpienia*/
        Agreement agreement = new Agreement();
        agreement.setCar(car);
        agreement.setCustomer(customer);
        agreement.setTransaction(Transaction.RENOUNCEMENT);
        agreement.setContent(carDto.getDescription());

        /**dodajemy umowe do auta i klienta*/
        car.addAgreement(agreement);
        customer.addAgreement(agreement);

        /**dodajemy zdrzenie do auta i klienta*/
        car.addEvent(event);
        customer.addEvents(event);

        /**zapisujemy zmiany*/
        carService.save(car);
        customerService.save(customer);
        eventService.save(event);

        return "/worker/cars-list";
    }

}
