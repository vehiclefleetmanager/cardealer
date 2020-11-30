package com.example.cardealer.employees.entity;

import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * This class describes the Workers in the application.
 */

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "employees")
//@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {

    private LocalDate employmentDate;

    private String employeeNumber;


    public Employee(String firstName, String lastName, String phoneNumber,
                    String email, String password, Role role, LocalDate employmentDate, String employeeNumber) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPassword(password);
        addRole(role);
        this.employmentDate = employmentDate;
        this.employeeNumber = employeeNumber;
    }
}
