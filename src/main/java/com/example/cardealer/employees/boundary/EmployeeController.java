package com.example.cardealer.employees.boundary;

import com.example.cardealer.employees.control.EmployeeService;
import com.example.cardealer.users.control.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CurrentUser currentUser;

    @GetMapping()
    public String getSystemEmployee(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("employees", employeeService.findAllEmployees(PageRequest.of(page, 10))
                .stream().map(EmployeeResponse::from).collect(Collectors.toList()));
        model.addAttribute("pages", employeeService.findAllEmployees(PageRequest.of(page, 10)));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser.getUser());
        return "employees/employees";
    }

    @PostMapping()
    public String addEmployee(@ModelAttribute("newEmployee") CreateEmployeeRequest request) {
        employeeService.addEmployee(request);
        return "redirect:/employees";
    }

    @PutMapping("/{id}")
    public String updateEmployee(@RequestParam("id") long id, @ModelAttribute("updateEmployee") UpdateEmployeeRequest request) {
        employeeService.updateEmployee(id, request);
        return "redirect:/employees";
    }

}
