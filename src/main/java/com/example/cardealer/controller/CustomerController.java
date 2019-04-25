
package com.example.cardealer.controller;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
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

    /*Metode należy zmienić tak aby wybierała usera/właściciela dynamicznie z bazy
     * w momencie kiedy ten loguje się na swoje konto*/
    @GetMapping("/customer")
    public String getOwnerMainPage(Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(1));
        return "/customer";
    }

    @PutMapping("/customer/update")
    public String updateActualOwner(@ModelAttribute("ownerDto") OwnerDto ownerDto) {
        ownerService.updateOwner(ownerDto);
        return "redirect:/customer";
    }

    @GetMapping("/customer/{ownerId}/update")
    public String getPageForUpdateOwnerById(@PathVariable Integer ownerId, Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(ownerId));
        return "/main/resources/templates/owner-update.html";
    }

    @GetMapping("/customer/{carId}/update")
    public String getPageForUpdateOwnerCarById(@PathVariable Integer carId, Model model) {
        model.addAttribute("car", carService.getCar(carId));
        return "customer/car-update";
    }

    @PutMapping("/customer/updateCar")
    public String updateCar(@ModelAttribute("carDto") CarDto carDto) {
        carService.updateCar(carDto);
        return "redirect:my-cars";
    }


    @GetMapping("/customer/car-add")
    public String getPageForRegisterNewCar(Model model) {
        model.addAttribute("car", new CarDto());
        return "owner-add-car";
    }

    @GetMapping("/customer/{ownerId}/car-add")
    public String getPageForRegisterNewCarByOwnerId(Model model) {
        model.addAttribute("car", new CarDto());
        return "owner-add-car";
    }


    @PostMapping("/customer/{ownerId}/addCar")
    public String addNewCarByOwnerId(@ModelAttribute("carDto") CarDto carDto, @PathVariable Integer ownerId) {
        carDto.setStatus(Car.Status.WAIT);
        carDto.setOwnerId(ownerService.getOwnerById(ownerId).getOwnerId());
        carDto.setTestDrive(0);
        carService.addCar(carDto);
        return "redirect:my-cars";
    }

    @PostMapping("/customer/addCar")
    public String addNewCar(@ModelAttribute("carDto") CarDto carDto) {
        carDto.setStatus(Car.Status.WAIT);
        carDto.setTestDrive(0);
        carService.addCar(carDto);
        return "redirect:my-cars";
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
