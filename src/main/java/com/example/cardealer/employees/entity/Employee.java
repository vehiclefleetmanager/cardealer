package com.example.cardealer.employees.entity;

import com.example.cardealer.users.entity.Person;
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
public class Employee extends Person {

    private LocalDate employmentDate;
    private String employeeNumber;


    public Employee(String firstName, String lastName, String address, String phoneNumber,
                    String email, LocalDate date, String employeeNumber) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        this.employmentDate = date;
        this.employeeNumber = employeeNumber;
    }

    public Employee(String firstName, String lastName, String phoneNumber,
                    String email, LocalDate date) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        this.employmentDate = date;
    }
}
