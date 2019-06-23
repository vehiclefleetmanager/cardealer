package com.example.cardealer.mappers;

import com.example.cardealer.model.Customer;
import com.example.cardealer.model.dtos.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDto> {
    @Override
    public CustomerDto map(Customer from) {
        return CustomerDto.builder()
                .id(from.getId())
                .address(from.getAddress())
                .email(from.getEmail())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .pesel(from.getPesel())
                .tin(from.getTin())
                .phoneNumber(from.getPhoneNumber())
                .build();
    }

    @Override
    public Customer reverse(CustomerDto to) {
        Customer customer = new Customer();
        customer.setFirstName(to.getFirstName());
        customer.setLastName(to.getLastName());
        customer.setPhoneNumber(to.getPhoneNumber());
        customer.setEmail(to.getEmail());
        return customer;
    }
}
