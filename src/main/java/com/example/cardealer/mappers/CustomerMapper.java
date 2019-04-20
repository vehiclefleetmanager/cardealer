package com.example.cardealer.mappers;

import com.example.cardealer.model.Customer;
import com.example.cardealer.model.dtos.CustomerDto;

public class CustomerMapper implements Mapper<Customer, CustomerDto>{
    @Override
    public CustomerDto map(Customer from) {
        return CustomerDto.builder()
                .address(from.getAddress())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .pesel(from.getPesel())
                .tin(from.getTin())
                .phoneNumber(from.getPhoneNumber())
                .build();
    }

    @Override
    public Customer reverse(CustomerDto to) {
        return Customer.builder()
                .build();
    }
}
