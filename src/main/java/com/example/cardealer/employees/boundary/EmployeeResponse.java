package com.example.cardealer.employees.boundary;

import com.example.cardealer.employees.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponse {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    boolean isActive;
    String employmentDate;
    String employeeNumber;

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhoneNumber(),
                employee.getEmail(),
                employee.isActive(),
                employee.getEmploymentDate().toString(),
                employee.getEmployeeNumber());
    }
}
