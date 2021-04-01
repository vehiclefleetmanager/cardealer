package com.example.cardealer.employees.control;

import com.example.cardealer.config.Clock;
import com.example.cardealer.config.TemporaryPassword;
import com.example.cardealer.employees.boundary.CreateEmployeeRequest;
import com.example.cardealer.employees.boundary.EmployeeRepository;
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
import java.util.stream.Collectors;

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

    public void addEmployee(CreateEmployeeRequest request) {
        String tempPass = password.temporaryPassword();
        User employee = new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                encoder.encode(tempPass),
                roleRepository.findByName("Worker"),
                clock.date(),
                setEmployeeNumber());
        System.out.println("Temporary password for " + request.getFirstName() + " set: " + tempPass);
        //TODO
        /*send pass o worker's email*/
        userRepository.save(employee);
    }

    private String setEmployeeNumber() {
        String prefix = "EN";
        List<User> allUsers = userRepository.findAll();
        List<User> onlyClientUsers = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .allMatch(role -> role.getName().
                                matches("CLIENT"))).collect(Collectors.toList());
        int employeeUsers = allUsers.size() - onlyClientUsers.size();
        return prefix + (employeeUsers + 1);
    }

    public void updateEmployee(long id, CreateEmployeeRequest request) {
        employeeRepository.findById(id).ifPresent(
                e -> {
                    e.setFirstName(request.getFirstName());
                    e.setLastName(request.getLastName());
                    e.setEmail(request.getEmail());
                    e.setPhoneNumber(request.getPhoneNumber());
                    e.setActive(request.isActive());
                    employeeRepository.save(e);
                });
        userRepository.findByEmail(request.getEmail()).ifPresent(
                u -> {
                    u.setRoles(setEmployeeRoles(request));
                    userRepository.save(u);
                }
        );
    }

    private Collection<Role> setEmployeeRoles(CreateEmployeeRequest request) {
        List<Role> roles = new ArrayList<>();
        for (String r : request.getRoles()) {
            roles.add(roleRepository.findByName(r));
        }
        return roles;
    }
}

