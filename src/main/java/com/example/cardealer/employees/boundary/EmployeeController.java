package com.example.cardealer.employees.boundary;

import com.example.cardealer.documents.boundary.InvoiceResponse;
import com.example.cardealer.documents.control.InvoiceService;
import com.example.cardealer.employees.control.EmployeeService;
import com.example.cardealer.events.boundary.CessionResponse;
import com.example.cardealer.events.control.CessionService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CessionService cessionService;
    private final InvoiceService invoiceService;
    private final CurrentUser currentUser;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_WORKER')")
    @GetMapping("/employees")
    public String getSystemEmployee(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("employees", employeeService.findAllEmployees(PageRequest.of(page, 10))
                .stream().map(EmployeeResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", employeeService.findAllEmployees(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "employees/employees";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_WORKER')")
    @PostMapping("/employees")
    public String addEmployee(@ModelAttribute("newEmployee") EmployeeRequest request) {
        employeeService.addEmployee(request);
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_WORKER')")
    @PutMapping("/employees")
    public String updateEmployee(@ModelAttribute("updateEmployee") EmployeeRequest request) {
        employeeService.updateEmployee(request);
        return "redirect:/employees";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_WORKER')")
    @GetMapping("/invoices")
    public String getSystemInvoices(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("invoices", invoiceService.findAll()
                .stream().map(InvoiceResponse::from).collect(toList()));
        model.addAttribute("pages", invoiceService.findAll(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "invoices/invoices";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_óWORKER')")
    @GetMapping("/cessions")
    public String getSystemCessions(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("cessions", cessionService.findAllCessions()
                .stream().map(CessionResponse::from).collect(toList()));
        model.addAttribute("pages", cessionService.findAllCessions(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "cessions/cessions";
    }
}
