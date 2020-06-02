package com.example.cardealer.customers.boundary;

import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String tin;
    private String pesel;
    private String idNumber;
    private String email;
}
