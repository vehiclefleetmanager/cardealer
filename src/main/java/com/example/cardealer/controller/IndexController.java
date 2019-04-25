package com.example.cardealer.controller;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Owner;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.OwnerDto;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Kontroler którego zadaniem jest porzedstawienie
oferty komisu na stronie głownej*/
@Controller
public class IndexController {

    private final CarService carService;
    private final OwnerService ownerService;

    @Autowired
    public IndexController(CarService carService,
                           OwnerService ownerService) {
        this.carService = carService;
        this.ownerService = ownerService;
    }

    /*Main page methods part*/
    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("cars", carService.getAvailableCars());
        model.addAttribute("markList", carService.findMark());
        model.addAttribute("modelList", carService.findModel());
        model.addAttribute("years", carService.findProductionYear());
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    /*Owner methods part*/
    @GetMapping("/owner")
    public String getOwnerPage(Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(1));
        return "owner";
    }

    @GetMapping("/owner/{ownerId}/owner-update")
    public String getUpdateOwnerPage(@PathVariable Integer ownerId, Model model) {
        model.addAttribute("owner", ownerService.getOwnerById(ownerId));
        return "owner-update";
    }

    @PutMapping("/updateOwner")
    public String updateOwner(@ModelAttribute("ownerDto") OwnerDto ownerDto) {
        ownerService.updateOwner(ownerDto);
        return "redirect:/owner";
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
        carDto.setStatus(Car.Status.WAIT);
        carDto.setTestDrive(0);
        carService.addCar(carDto);
        return "redirect:/owner";
    }


    @GetMapping("/sale")
    public String getPageForRegisterNewOwner(Model model) {
        model.addAttribute("person", new OwnerDto());
        return "person-add";
    }

    @PostMapping("/addPerson")
    public String addNewOwner(@ModelAttribute("ownerDto") OwnerDto ownerDto) {
        ownerDto.setStatus(Owner.Status.PRESENT);
        ownerService.addOwner(ownerDto);
        return "redirect:add-car";
    }

    @GetMapping("/owner/{ownerId}/cars")
    public String getPageWhereIsListCarsOfOwner(Model model, @PathVariable Integer ownerId) {
        model.addAttribute("cars", carService.getCarsByOwnersIds());
        model.addAttribute("owner", ownerService.getOwnerById(ownerId));
        return "cars-list";
    }

    @GetMapping("/owner/{carId}/details")
    public String getPageWhereIsDetailsCar(Model model, @PathVariable Integer carId) {
        model.addAttribute("car", carService.getCar(carId));
        return "details";
    }


    /*@PostMapping("/sale")
    public String addCarToSale(@ModelAttribute CarDto carDto){
        carService.addCar(carDto);
        return "customer/sale";
    }*/


    /*@RequestMapping
    public String showCars(@ModelAttribute("carDto") CarDto carDto, Model model) {
        List<Car> cars = carService.findByRenouncementLike(Transaction.RENOUNCEMENT);
        model.addAttribute("cars", cars);

        List<String> markList = carService.findMark();
        model.addAttribute("markList", markList);

        List<String> modelList = carService.findModel();
        model.addAttribute("modelList", modelList);

        List<Integer> years = carService.findProductionYear();
        model.addAttribute("years", years);

        return "index";
    }

    @PostMapping("/filter")
    public String filters(@ModelAttribute("carDto") CarDto carDto,
                          @RequestParam("mark") String carMark,
                          @RequestParam("model") String carModel, Model model) {
        List<Car> cars = carService.findCarByMark(carMark);
        carService.findCarsByModel(carModel);

        model.addAttribute("cars", cars);
        return "index";
    }
*/
}