package com.example.cardealer.web_controller;

import com.example.cardealer.cars.boundary.CarResponse;
import com.example.cardealer.cars.control.CarService;
import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.customers.boundary.CustomerResponse;
import com.example.cardealer.customers.control.CustomerService;
import com.example.cardealer.documents.boundary.AgreementResponse;
import com.example.cardealer.documents.boundary.InvoiceResponse;
import com.example.cardealer.events.boundary.CreateTestDriveRequest;
import com.example.cardealer.users.boundary.CreateUserRequest;
import com.example.cardealer.users.boundary.UpdateUserRequest;
import com.example.cardealer.users.control.CurrentUser;
import com.example.cardealer.users.control.UserService;
import com.example.cardealer.users.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class IndexController {

    private final CarService carService;
    private final CustomerService customerService;

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

    @GetMapping("/userArea")
    public String getUserAreaInformation(Model model) {
        model.addAttribute("userInfo", userService.findUser(
                currentUser.getUser().getId()));
        model.addAttribute("userCars", carService.findAllCarsOfUser(
                currentUser.getUser().getId(), Pageable.unpaged()));
        model.addAttribute("currentUser", currentUser.getUser());
        return "fragments/userArea";
    }

    @GetMapping("/details/{id}")
    public String showCarsDetails(@PathVariable("id") Long id, Model model) {
        Car detailsCar = carService.findCar(id);
        boolean isYourCar;
        boolean isCustomer;
        try {
            User activeUser = currentUser.getUser();
            isCustomer = activeUser.getRoles().stream().anyMatch(r -> r.getName().matches("CLIENT"));
            isYourCar = detailsCar.getCarOwner().getId().toString().matches(activeUser.getId().toString()) && isCustomer;
        } catch (Exception e) {
            isYourCar = false;
            return setAttributesWhenWantShowDetailsCar(model, detailsCar, isYourCar);
        }
        return setAttributesWhenWantShowDetailsCar(model, detailsCar, isYourCar);
    }

    @GetMapping("/more/{id}")
    public String showCustomersDetails(@PathVariable("id") Long id, Model model) {
        CustomerResponse detailsCustomer = CustomerResponse.from(customerService.findCustomer(id));
        Collection<CarResponse> cars = new ArrayList<>();
        Collection<AgreementResponse> agreements = new ArrayList<>();
        Collection<InvoiceResponse> invoices = new ArrayList<>();
        boolean isCustomer;
        try {
            User activeUser = currentUser.getUser();
            isCustomer = activeUser.getRoles().stream().anyMatch(r -> r.getName().matches("CLIENT"));
            if (!isCustomer) {
                cars = customerService.findCars(detailsCustomer.getId()).stream().map(CarResponse::from).collect(Collectors.toList());
                agreements = customerService.findAgreements(detailsCustomer.getId()).stream().map(AgreementResponse::from).collect(Collectors.toList());
                invoices = customerService.findInvoices(detailsCustomer.getId()).stream().map(InvoiceResponse::from).collect(Collectors.toList());
                return setAttributesWhenWantShowDetailsCustomer(model, detailsCustomer, cars, agreements, invoices);
            }
        } catch (Exception e) {
            return "customers";
        }
        return "customers";
    }


    @PutMapping("/updateUserInfo")
    public String editUserInfo(@ModelAttribute("updateUserInfo") UpdateUserRequest request) {
        userService.updateUserInformation(request);
        return "redirect:/userArea";
    }

    @PostMapping("/test/{id}")
    public String testDriveReservation(@PathVariable("id") Long id,
                                       @ModelAttribute("newTestDrive") CreateTestDriveRequest request,
                                       Model model, RedirectAttributes attributes) {
        String flag = carService.testDriveReservation(request);
        if (flag.matches("ok")) {
            setAnonymousUser(model);
            attributes.addFlashAttribute("flag", true);
            attributes.addFlashAttribute("msg",
                    "Dokonano wstępnej rezerwazcji jazdy testowej samochodem " + getCarBasicInfo(id) + " na dzień " +
                            request.getDate() + " na godzinę " + request.getTime());
            return "redirect:../details/" + id;
        } else {
            model.addAttribute("flag", flag);
            model.addAttribute("time", "Godzina " + request.getTime() + " jest już zajęta. Podaj inną godzinę.");
            return showCarsDetails(id, model);
        }
    }

    @PostMapping("/search")
    public String filterCarOnMainPage(
            @RequestParam(value = "mark", required = false) @PathVariable String carMark,
            @RequestParam(value = "maxYear", required = false) @PathVariable String maxYear,
            @RequestParam(value = "maxPrice", required = false) @PathVariable String maxPrice,
            Model model) {
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

    private String getCarBasicInfo(Long id) {
        return carService.findCar(id).getMark() + " " +
                carService.findCar(id).getModel() + " " +
                carService.findCar(id).getBodyNumber();
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

    private String setAttributesWhenWantShowDetailsCar(Model model, Car detailsCar, boolean isYourCar) {
        setAnonymousUser(model);
        model.addAttribute("car", detailsCar);
        model.addAttribute("isYourCar", isYourCar);
        model.addAttribute("status", detailsCar.getStatus());
        return "cars/details";
    }

    private String setAttributesWhenWantShowDetailsCustomer(Model model, CustomerResponse customer,
                                                            Collection<CarResponse> cars, Collection<AgreementResponse> agreements, Collection<InvoiceResponse> invoices) {
        setAnonymousUser(model);
        model.addAttribute("customer", customer);
        model.addAttribute("cars", cars);
        model.addAttribute("agreements", agreements);
        model.addAttribute("invoices", invoices);
        return "customers/more";
    }
}