package com.example.cardealer.customers.boundary;

import com.example.cardealer.customers.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponse {
    Long id;
    String firstName;
    String lastName;
    String address;
    String phoneNumber;
    String pesel;
    String tin;
    String idNumber;
    String email;

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getPesel(),
                customer.getTin(),
                customer.getIdNumber(),
                customer.getEmail()
        );
    }

}
