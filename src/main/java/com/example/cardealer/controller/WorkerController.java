package com.example.cardealer.controller;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
import com.example.cardealer.model.Owner;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.EventDto;
import com.example.cardealer.model.dtos.OwnerDto;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.service.*;
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
    private final OwnerService ownerService;
    private final InvoiceService invoiceService;

    @Autowired
    public WorkerController(CustomerService customerService,
                            CarService carService,
                            EventService eventService,
                            AgreementService agreementService,
                            OwnerService ownerService,
                            InvoiceService invoiceService) {
        this.customerService = customerService;
        this.carService = carService;
        this.eventService = eventService;
        this.agreementService = agreementService;
        this.ownerService = ownerService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/agreements")
    public String getPageToShowAgreementsInWorkerPanel(Model model) {
        model.addAttribute("title", "wybierz rodzaj umowy");
        return "worker/agreement";
    }

    @GetMapping("/cession")
    public String getPageToShowCessionsAgreements(Model model){
        model.addAttribute("title", "lista zawartych umów przekazania do serwisu");
        model.addAttribute("agreements", agreementService.getAgreementsDtoByRenouncement());
        return "worker/agreements";
    }

    @GetMapping("/sell")
    public String getPageToShowSellAgreements(Model model){
        model.addAttribute("title", "lista zawartych umów sprzedaży");
        model.addAttribute("agreements", agreementService.getAgreementsDtoBySell());
        return "worker/agreements";
    }

    @GetMapping("/invoices")
    public String getPageToShowInvoices(Model model){
        model.addAttribute("title", "wybierz rodzaj faktury");
        return "worker/invoice";
    }

    @GetMapping("/selling")
    public String getPageToShowSalesInvoice(Model model){
        model.addAttribute("title", "lista faktur sprzedaży");
        model.addAttribute("invoices", invoiceService.getInvoiceDtoBySold());
        return "worker/invoices";
    }

    @GetMapping("/buying")
    public String getPageToShowPurchaseInvoice(Model model){
        model.addAttribute("title", "lista faktur zakupu");
        model.addAttribute("invoices", invoiceService.getInvoiceDtoByBought());
        return "worker/invoices";
    }

    @GetMapping("/transactions")
    public String getPageToSetTransactionInWorkerPanel(Model model) {
        model.addAttribute("title", "wybierz transkacje do wykonania");
        return "worker/transactions";
    }

    @GetMapping("/sellCar")
    public String getAllCarsToSellInWorkerPanel(Model model) {
        model.addAttribute("title", "dokonaj sprzedaży pojazdu");
        model.addAttribute("cars", carService.getAvailableCarsWithOwnerPersonalData());
        return "worker/sellCar";
    }

    @GetMapping("/sellCar/{id}")
    public String getFormToChangeOwner(@PathVariable Integer id, Model model) {
        model.addAttribute("carDto", carService.getCarDto(id));
        model.addAttribute("person", new Owner());
        return "new-owner-add";
    }

    @PostMapping("/sell/{id}")
    public String doSellCar(
            @PathVariable Integer id,
            @ModelAttribute("person") Owner owner,
            @ModelAttribute("carDto") Car car) {
        carService.sellingCar(owner, carService.getCar(id));
        return "redirect:/worker/sellCar";
    }

    @GetMapping("/buyCar")
    public String getFormToAddCar(Model model){
        model.addAttribute("person", new OwnerDto());
        return "person-add";
    }

    @PostMapping("/addPerson")
    public String doAddNewOwner(@ModelAttribute("ownerDto") OwnerDto ownerDto) {
        ownerDto.setStatus(Owner.Status.PRESENT);
        Owner owner = ownerService.addOwner(ownerDto);
        Integer ownerId = owner.getOwnerId();
        return "redirect:/" + ownerId + "/add-car";
    }

    @GetMapping("/{ownerId}/add-car")
    public String getPageForRegisterNewCarView(@PathVariable Integer ownerId, Model model) {
        model.addAttribute("carDto", new CarDto());
        model.addAttribute("owner", ownerService.getOwnerById(ownerId));
        return "add-car";
    }

    @PostMapping("/{ownerId}/addCar")
    public String doAddNewCar(@ModelAttribute("carDto") CarDto carDto, @PathVariable Integer ownerId) {
        carDto.setStatus(Car.Status.BOUGHT);
        carDto.setOwnerId(ownerId);
        carDto.setTestDrive(0);
        carService.addCar(carDto);
        return "redirect:/worker/transactions";
    }

    @GetMapping("/acceptCar")
    public String getAllCarsToAcceptInWorkerPanel(Model model) {
        model.addAttribute("title", "dokonaj przyjęcia auta do oferty komisu");
        model.addAttribute("cars", carService.getWaitingCarsWithOwnerPersonalData());
        return "worker/acceptCar";
    }

    @GetMapping("/acceptCar/{id}")
    public String doAcceptCar(@PathVariable Integer id) {
        carService.addingCarToOfferCommission(id);
        return "redirect:/worker/acceptCar";
    }

    @GetMapping("/rejectCar/{id}")
    public String doRejectCar(@PathVariable Integer id) {
        Car databaseCar = carService.getCar(id);
        databaseCar.setStatus(Car.Status.REJECTED);
        carService.save(databaseCar);
        return "redirect:/worker/acceptCar";
    }

    @GetMapping("/details/{id}")
    public String showDetailsCarBeforeAccept(@PathVariable Integer id, Model model) {
        model.addAttribute("carDto", carService.getCar(id));
        return "details";
    }

    @GetMapping("/cars")
    public String getAllCarsInWorkerPanel(Model model) {
        model.addAttribute("cars", carService.getCarsDtoAndOwnersName());
        return "worker/cars";
    }

    @GetMapping("/customers")
    public String getPageToShowCustomers(Model model) {
        model.addAttribute("title", "klienci komisu");
        return "worker/customer";
    }

    @GetMapping("/all-customers")
    public String getPageToShowAllCustomers(Model model) {
        model.addAttribute("title", "lista wszystkich klientów komisu");
        model.addAttribute("customers", customerService.getCustomersDto());
        return "worker/all-customers";
    }

    @GetMapping("/interested-customers")
    public String getPageToShowInterestedCustomers(Model model) {
        model.addAttribute("title", "klienci zaintersowani ofertą komisu");
        model.addAttribute("customers", customerService.getCustomerDtoBodyNumberMarkAndModelOfCheckedCar());
        return "worker/int-customers";
    }

    @GetMapping("/{id}/person-update")
    public String getFormToUpdateCustomer(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person", customerService.getCustomer(id));
        return "person-update";
    }

    @GetMapping("/customer-add")
    public String getFormToAddCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "/worker/customer-edit";
    }

    @PostMapping("/customer-save")
    public String doAddCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/worker/customers-list";
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

    @GetMapping("{id}/details")
    public String showDetailsCar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("carDto", carService.getCar(id));
        return "details";
    }

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

    /*@GetMapping("/show/car?transaction=WAITING")
    public String showCarsWaiting(@ModelAttribute("carDto") CarDto carDto, @RequestParam("transaction") Transaction transaction, Model model) {
        model.addAttribute("cars", carService.findByWaitingLike(transaction));
        return "/worker/cars-waiting";
    }
*/

    /*@GetMapping("/{id}/delete-customer")
    public String deleteCustomer(@PathVariable("id") Integer id, Customer customer) {
        id = customer.getCustomerNumber();
        customerService.deleteById(id);
        return "redirect:/worker/customers-list";
    }*/


    /*@GetMapping("/cars-list")
    public String showCars(@ModelAttribute("carDto") CarDto carDto, Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "/worker/cars-list";
    }*/

    /*Filtrowanie list po typie transakcji pojazdu*/
    /*@GetMapping("/cars-list-filter")
    public String showCarsFilter(@ModelAttribute("carDto") CarDto carDto, @RequestParam("transaction") Transaction transaction, Model model) {
        List<Car> carByTransactionLike = carService.findCarByTransactionLike(transaction);
        model.addAttribute("cars", carByTransactionLike);
        return "/worker/cars-list";
    }*/



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


}
