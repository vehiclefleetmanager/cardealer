package com.example.cardealer.events.boundary;

import com.example.cardealer.cars.control.CarService;
import com.example.cardealer.events.control.TestDriveService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/reservations")
@AllArgsConstructor
public class TestDriveController {
    private final TestDriveService testDriveService;
    private final CurrentUser currentUser;
    private final CarService carService;

    @GetMapping
    public String getSystemReservations(@RequestParam(defaultValue = "0") int page, Model model) {
        if (currentUser.getUser().getRoles().stream().anyMatch(role -> role.getName().matches("CLIENT"))) {
            model.addAttribute("reservations", testDriveService.findAllReservationsOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10))
                    .stream().map(TestDriveResponse::from).collect(Collectors.toList()));
            model.addAttribute("pages", testDriveService.findAllReservationsOfUser(currentUser.getUser().getId(), PageRequest.of(page, 10)));
        } else {
            model.addAttribute("reservations", testDriveService.findAllReservations(PageRequest.of(page, 10))
                    .stream().map(TestDriveResponse::from).collect(Collectors.toList()));
            model.addAttribute("pages", testDriveService.findAllReservations(PageRequest.of(page, 10)));
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "reservations/reservations";
    }

    @PutMapping("/{id}")
    public String updateDriveReservation(@PathVariable("id") Long id,
                                         @ModelAttribute("updateTestDrive") UpdateTestDriveRequest request,
                                         RedirectAttributes attributes) {
        carService.updateTestDriveReservation(id, request);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Dokonano zmian w rezerwacji numer " + id);
        return "redirect:/reservations";
    }
}
