package com.example.cardealer.web_controller;

import com.example.cardealer.cars.boundary.CarResponse;
import com.example.cardealer.cars.boundary.CreateTestDriveRequest;
import com.example.cardealer.cars.control.CarService;
import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.users.boundary.CreateUserRequest;
import com.example.cardealer.users.control.CurrentUser;
import com.example.cardealer.users.control.UserService;
import com.example.cardealer.users.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class IndexController {

    private final CarService carService;
    private final UserService userService;
    private final CurrentUser currentUser;

    @GetMapping
    public String getMainApplicationPageView(@RequestParam(defaultValue = "0") int page, Model model) {
        setAnonymousUser(model);
        model.addAttribute("cars", carService.findAllAvailableCars(PageRequest.of(page, 6))
                .stream().map(CarResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", carService.findAllAvailableCars(PageRequest.of(page, 6)));
        model.addAttribute("currentPage", page);
        prepareSearchFormFields(model);
        return "index";
    }

    @GetMapping("/details/{id}")
    public String showCarsDetails(@PathVariable("id") Long id, Model model) {
        setAnonymousUser(model);
        model.addAttribute("car", carService.findCar(id));
        return "cars/details";
    }

    @PostMapping("/test/{id}")
    public String testDriveReservation(@PathVariable("id") Long id,
                                       @ModelAttribute("newTestDrive") CreateTestDriveRequest request,
                                       Model model) {
        String flag = carService.testDriveReservation(request);
        if (flag.matches("ok")) {
            setAnonymousUser(model);
            return "redirect:../details/" + id;
        } else {
            model.addAttribute("flag", flag);
            return showCarsDetails(id, model);
        }
    }

    private void setAnonymousUser(Model model) {
        try {
            model.addAttribute("currentUser", currentUser.getUser());
        } catch (ClassCastException ex) {
            CurrentUser anonymousUser = () -> {
                User user = new User();
                user.setFirstName("Anonymus");
                return user;
            };
            model.addAttribute("currentUser", anonymousUser.getUser());
        }
    }

    @PostMapping("/search")
    public String filterCarOnMainPage(
            @RequestParam(value = "mark", required = false) @PathVariable String carMark,
            @RequestParam(value = "maxYear", required = false) @PathVariable String maxYear,
            @RequestParam(value = "maxPrice", required = false) @PathVariable String maxPrice, Model model) {
        List<Car> carsFromSearchButton = carService.getCarsFromSearchButton(carMark, maxYear, maxPrice);
        model.addAttribute("cars", carsFromSearchButton.stream().map(CarResponse::from).collect(Collectors.toList()));
        setAnonymousUser(model);
        prepareSearchFormFields(model);
        return "result";
    }

    @GetMapping("/login")
    public String getViewLoginPageInApplication() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogInApplication() {
        return "redirect:/";
    }

    /*REGISTRATION*/
    @GetMapping("/register")
    public String getViewRegisterPage(Model model) {
        model.addAttribute("newUser", new CreateUserRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String doRegisterNewOwner(@ModelAttribute("newUser") @Valid CreateUserRequest request,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/registration";
        }
        userService.registrationNewAppUser(request);
        return "redirect:/login";
    }
    /*END REGISTRATION*/

    private void prepareSearchFormFields(Model model) {
        model.addAttribute("markList", carService.findMark());
        model.addAttribute("years", carService.findProductionYear());
    }
}