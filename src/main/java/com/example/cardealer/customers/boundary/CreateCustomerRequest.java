package com.example.cardealer.customers.boundary;

import com.example.cardealer.cars.entity.Car;
import lombok.Data;

import java.util.Set;

@Data
public class CreateCustomerRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String tin;
    private String pesel;
    private String idNumber;
    private String email;
    private Set<Car> cars;
}
