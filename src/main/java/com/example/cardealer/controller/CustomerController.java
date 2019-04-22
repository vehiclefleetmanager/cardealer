
package com.example.cardealer.controller;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Owner;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.CustomerDto;
import com.example.cardealer.model.dtos.OwnerDto;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.CustomerService;
import com.example.cardealer.service.OwnerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Data
@Controller
public class CustomerController {

    private CustomerService customerService;
    private OwnerService ownerService;
    private CarService carService;

    @Autowired
    public CustomerController(CustomerService customerService,
                              OwnerService ownerService,
                              CarService carService) {
        this.customerService = customerService;
        this.ownerService = ownerService;
        this.carService = carService;
    }

    @GetMapping("/customer")
    public String customerPage(Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(15));
        return "customer/customer";
    }

    @PutMapping("/customer/update")
    public String updateOwner(@ModelAttribute OwnerDto ownerDto) {
        ownerService.updateOwner(ownerDto);
        return "redirect:/customer";
    }

    @GetMapping("/customer/{id}/update")
    public String getUpdateOwnerPage(@PathVariable Integer id, Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(id));
        return "customer/owner-update";
    }

    @GetMapping("/customer/sale")
    public String registerOwner(Model model) {
        model.addAttribute("owner", new OwnerDto());
        return "customer/owner-add";
    }

    @PostMapping("/customer/addOwner")
    public String addOwner(@ModelAttribute OwnerDto ownerDto) {
        ownerDto.setStatus(Owner.Status.PRESENT);
        ownerService.addOwner(ownerDto);
        return "redirect:car-add";
    }

    @GetMapping("/customer/{id}/car-add")
    public String registerCar(Model model, @PathVariable Integer id) {
        model.addAttribute("owner", ownerService.getOwnerById(id));
        model.addAttribute("car", new CarDto());
        return "customer/car-add";
    }

    @PostMapping("/customer/{id}/addCar")
    public String addCar(@ModelAttribute CarDto carDto, @PathVariable Integer id) {
        Owner owner = ownerService.getOwnerById(id);
        carDto.setOwner(owner);
        carDto.setStatus(Car.Status.WAIT);
        carService.addCar(carDto);
        return "redirect:cars";
    }

    @GetMapping("/customer/{id}/cars")
    public String getCarsList(Model model) {
        model.addAttribute("cars", carService.getCarsWhereOwnerIsPresent());
        return "customer/my-cars";
    }

    /*##########################*/

    @GetMapping("/customers/{tinNumber}")
    public CustomerDto getCustomerByTinNumber(@RequestParam(value = "tin_number", required = false)
                                              @PathVariable Integer tinNumber) {
        return customerService.getCustomerDtoByTinNumber(tinNumber);
    }

    @GetMapping("/customers/{pesel_number}")
    public CustomerDto getCustomerByPeselNumber(@RequestParam(value = "pesel_number", required = false)
                                                @PathVariable String peselNumber) {
        return customerService.getCustomerDtoByPesel(peselNumber);
    }

    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }


    @DeleteMapping("/customers/{pesel_number}")
    public void deleteCustomer(@PathVariable String pesel_number) {
        customerService.deleteCustomerByPeselNumber(pesel_number);
    }

  /*  @PostMapping("/sale")
    public Customer addOwnerToSaleCar(@RequestBody CustomerDto customerDto){
        return customerService.addCustomer(customerDto);
    }*/

}
/* @PostMapping("/new/save")
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
    }*//*


 */
/*@PostMapping("/add-meet")
    public String addMeetToTestDrive(@ModelAttribute("carDto") CarDto carDto, EventDto eventDto) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(eventDto.getEventDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Car databaseCar = carService.getCar(carDto.getId());
        *//*
 */
/*aktualizacja stanu jazd testowych*//*
 */
/*
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
*//*


}
*/
