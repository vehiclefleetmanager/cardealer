package com.example.cardealer.customers.boundary;

import com.example.cardealer.customers.control.CustomerService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CurrentUser currentUser;

    @GetMapping
    public String getSystemClients(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("customers", customerService.findAllCustomers(PageRequest.of(page, 10))
                .stream().map(CustomerResponse::from).collect(toList()));
        model.addAttribute("pages", customerService.findAllCustomers(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "customers/customers";
    }

    @PostMapping
    public String addCustomer(@ModelAttribute("newCustomer") CreateCustomerRequest request,
                              RedirectAttributes attributes) {
        customerService.addCustomer(request);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Dodano nowego klienta komisu");
        return "redirect:/customers";
    }


    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable("id") Long id,
                                 @ModelAttribute("updateCustomer") UpdateCustomerRequest request,
                                 RedirectAttributes attributes) {
        customerService.updateCustomer(id, request);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Zmieniono dane klienta numer " + id);
        return "redirect:/customers";
    }

    @PostMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id,
                                 RedirectAttributes attributes) {
        String customerName = customerService.findCustomer(id).getFirstName() + " "
                + customerService.findCustomer(id).getLastName();
        customerService.deleteCustomer(id);
        attributes.addFlashAttribute("flag", true);
        attributes.addFlashAttribute("msg", "Usunięto klienta komisu " + customerName);
        return "redirect:/customers";
    }

    @PostMapping("/search")
    public String filterCustomerList(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(value = "firstName", required = false) @PathVariable String firstName,
                                     @RequestParam(value = "lastName", required = false) @PathVariable String lastName,
                                     @RequestParam(value = "pesel", required = false) @PathVariable String pesel,
                                     @RequestParam(value = "tin", required = false) @PathVariable String tin,
                                     Model model) {
        model.addAttribute("customers",
                customerService.getCustomersFromSearchButton(firstName, lastName, pesel,
                        tin).stream().map(CustomerResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages",
                customerService.getCustomersFromSearchButton(firstName, lastName, pesel,
                        tin, PageRequest.of(page, 10)));
        model.addAttribute("currentUser", currentUser.getUser());
        return "customers/customers";
    }
}