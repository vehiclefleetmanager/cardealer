package com.example.cardealer.employees.boundary;

import lombok.Data;

import java.util.Collection;

@Data
public class UpdateEmployeeRequest {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String address;
    boolean isActive;
    Collection<String> roles;
}
