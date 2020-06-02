package com.example.cardealer.employees.control;

import com.example.cardealer.config.Clock;
import com.example.cardealer.config.TemporaryPassword;
import com.example.cardealer.employees.boundary.EmployeeRepository;
import com.example.cardealer.employees.boundary.EmployeeRequest;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.users.boundary.RoleRepository;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final TemporaryPassword password;
    private final Clock clock;

    public Page<Employee> findAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public void addEmployee(EmployeeRequest request) {
        Employee employee = new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                clock.date());
        Employee newEmployee = employeeRepository.save(employee);
        String tempPass = password.temporaryPassword();
        System.out.println("Temporary password for " + newEmployee.getFirstName() + " set: " + tempPass);
        User userSystem = new User(newEmployee, true, encoder.encode(tempPass));
        userSystem.setRoles(setEmployeeRoles(request));
        userRepository.save(userSystem);
    }

    public void updateEmployee(EmployeeRequest request) {
        employeeRepository.findById(request.getId()).ifPresent(
                e -> {
                    e.setFirstName(request.getFirstName());
                    e.setLastName(request.getLastName());
                    e.setEmail(request.getEmail());
                    e.setPhoneNumber(request.getPhoneNumber());
                    e.setAddress(request.getAddress());
                    employeeRepository.save(e);
                });
        userRepository.findByEmail(request.getEmail()).ifPresent(
                u -> {
                    u.setRoles(setEmployeeRoles(request));
                    userRepository.save(u);
                }
        );
    }

    private Collection<Role> setEmployeeRoles(EmployeeRequest request) {
        List<Role> roles = new ArrayList<>();
        for (String r : request.getRoles()) {
            roles.add(roleRepository.findByName(r));
        }
        return roles;
    }
}

