
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CurrentUser currentUser;
    private final CustomerService customerService;

    @GetMapping()
    public String getSystemCars(@RequestParam(defaultValue = "0") int page, Model model) {
        if (currentUser.getUser().getRoles().stream().anyMatch(role -> role.getName().matches("CLIENT"))) {
            model.addAttribute("cars", carService.findAllCarsOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10))
                    .stream().map(CarResponse::from).collect(Collectors.toList()));
            model.addAttribute("pages", carService.findAllCarsOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10)));
        } else {
            model.addAttribute("cars", carService.findAllCars(PageRequest.of(page, 10))
                    .stream().map(CarResponse::from).collect(Collectors.toList()));
            model.addAttribute("pages", carService.findAllCars(PageRequest.of(page, 10)));
        }
        model.addAttribute("newCessionEvent", new CreateCessionRequest());
        model.addAttribute("currentPage", page);
        model.addAttribute("markList", carService.findMark());
        model.addAttribute("currentUser", currentUser.getUser());
        return "cars/cars";
    }

    @PostMapping()
    public String addCar(@Valid @ModelAttribute("newCessionEvent") CreateCessionRequest request,
                         RedirectAttributes attributes) {
        String carInfo = request.getMark() + " " + request.getModel() + " " + request.getBodyNumber();
        carService.addCarAndCreateCessionEventAndCessionAgreement(request, currentUser.getUser().getId());
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Utworzono dokument cesji dla samochodu " + carInfo);
        return "redirect:/cars";
    }

    @PutMapping("/{id}")
    public String updateCar(@PathVariable("id") Long id,
                            @ModelAttribute("updateCar") UpdateCarRequest request,
                            RedirectAttributes attributes) {
        carService.updateCar(id, request);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Zmieniono dane samochodu numer " + id);
        return "redirect:/cars";
    }

    @PostMapping("/repair/{id}")
    public String repairCar(@PathVariable("id") Long id,
                            @ModelAttribute("newRepairEvent") CreateRepairRequest request,
                            RedirectAttributes attributes) {
        Long employeeId = currentUser.getUser().getId();
        carService.repairCar(id, request, employeeId);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Utowrzono dokument naprawy samochodu " + getCarBasicInfo(id));
        return "redirect:/cars";
    }

    @GetMapping("/sale/{id}")
    public String saleCar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("car", carService.findCar(id));
        model.addAttribute("customers", customerService.findAll()
                .stream().map(CustomerResponse::from).collect(Collectors.toList()));
        model.addAttribute("currentUser", currentUser.getUser());
        return "cars/sale";
    }

    @PostMapping("/sale")
    public String saleCar(@ModelAttribute("newSale") CreateCarSaleRequest request,
                          RedirectAttributes attributes) {
        Long employeeId = currentUser.getUser().getId();
        if (carService.saleCar(request, employeeId)) {
            attributes.addFlashAttribute("flag", true);
            attributes.addFlashAttribute("msg", "Utworzono dokumenty sprzedaży samochodu " + getCarBasicInfo(request.getCarId()));
        } else {
            attributes.addFlashAttribute("bad", true);
            attributes.addFlashAttribute("badMsg", "Operacja utworzenia dokumentów sprzedaży nie powiodła sie! ");
        }
        return "redirect:/cars";
    }

    @PostMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") Long id,
                            RedirectAttributes attributes) {
        String carInfo = carService.findCar(id).getMark() + " " +
                carService.findCar(id).getModel() + " " +
                carService.findCar(id).getBodyNumber();
        User userSystem = currentUser.getUser();
        carService.deleteCar(id, userSystem);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Usunięto samochód " + carInfo);
        return "redirect:/cars";
    }

    @PostMapping("/search")
    public String filterCarList(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(value = "mark", required = false) @PathVariable String carMark,
                                @RequestParam(value = "maxYear", required = false) @PathVariable String maxYear,
                                @RequestParam(value = "maxPrice", required = false) @PathVariable String maxPrice,
                                @RequestParam(value = "status", required = false) @PathVariable String status,
                                Model model) {
        model.addAttribute("cars", carService.getCarsFromSearchButton(carMark, maxYear,
                maxPrice, status, PageRequest.of(page, 10))
                .stream().map(CarResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", carService.getCarsFromSearchButton(carMark, maxYear,
                maxPrice, status, PageRequest.of(page, 10)));
        model.addAttribute("markList", carService.findMark());
        model.addAttribute("currentUser", currentUser.getUser());
        return "cars/cars";
    }

    private String getCarBasicInfo(Long id) {
        return carService.findCar(id).getMark() + " " +
                carService.findCar(id).getModel() + " " +
                carService.findCar(id).getBodyNumber();
    }
}
