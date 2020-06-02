
package com.example.cardealer.cars.boundary;

import com.example.cardealer.cars.control.CarService;
import com.example.cardealer.customers.boundary.CustomerResponse;
import com.example.cardealer.customers.control.CustomerService;
import com.example.cardealer.events.boundary.CreateCarSaleRequest;
import com.example.cardealer.events.boundary.CreateCessionRequest;
import com.example.cardealer.events.boundary.CreateRepairRequest;
import com.example.cardealer.users.control.CurrentUser;
import com.example.cardealer.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CurrentUser currentUser;
    private final CustomerService customerService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MECHANIC','ROLE_WORKER')")
    @GetMapping()
    public String getSystemCars(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("cars", carService.findAllCars(PageRequest.of(page, 10))
                .stream().map(CarResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", carService.findAllCars(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("markList", carService.findMark());
        model.addAttribute("modelList", carService.findModel());
        model.addAttribute("currentUser", currentUser.getUser());
        return "cars/cars";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WORKER')")
    @PostMapping
    public String addCar(@ModelAttribute("newCessionEvent") CreateCessionRequest request) {
        carService.addCarAndCreateCessionEvent(request, currentUser.getUser().getId());
        return "redirect:/cars";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
    @PutMapping("/update/{id}")
    public String updateCar(@PathVariable("id") Long id,
                            @ModelAttribute("updateCar") UpdateCarRequest request) {
        carService.updateCar(id, request);
        return "redirect:/cars";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MECHANIC')")
    @PostMapping("/repair/{id}")
    public String repairCar(@PathVariable("id") Long id,
                            @ModelAttribute("newRepairEvent") CreateRepairRequest request) {
        Long employeeId = currentUser.getUser().getId();
        carService.repairCar(id, request, employeeId);
        return "redirect:/cars";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WORKER')")
    @GetMapping("/sale/{id}")
    public String saleCar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car", carService.findCar(id));
        model.addAttribute("customers", customerService.findAll()
                .stream().map(CustomerResponse::from).collect(Collectors.toList()));
        model.addAttribute("currentUser", currentUser.getUser());
        return "cars/sale";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WORKER')")
    @PostMapping("/sale")
    public String saleCar(@ModelAttribute("newSale") CreateCarSaleRequest request) {
        Long employeeId = currentUser.getUser().getId();
        carService.saleCar(request.getCarId(), request.getNewOwnerId(), request.getPrice(), employeeId);
        return "redirect:/cars";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WORKER')")
    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") Long id) {
        User userSystem = currentUser.getUser();
        carService.deleteCar(id, userSystem);
        return "redirect:/cars";
    }

    @PostMapping("/search")
    public String filterCarList(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(value = "mark", required = false) @PathVariable String carMark,
                                @RequestParam(value = "maxYear", required = false) @PathVariable String maxYear,
                                @RequestParam(value = "maxPrice", required = false) @PathVariable String maxPrice,
                                Model model) {
        model.addAttribute("cars", carService.getCarsFromSearchButton(carMark, maxYear,
                maxPrice).stream().map(CarResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", carService.getCarsFromSearchButton(carMark, maxYear, maxPrice, PageRequest.of(page, 10)));
        model.addAttribute("markList", carService.findMark());
        model.addAttribute("currentUser", currentUser.getUser());
        return "cars/cars";
    }
}
