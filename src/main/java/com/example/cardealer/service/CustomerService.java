package com.example.cardealer.service;

import com.example.cardealer.model.Customer;
import com.example.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Integer id) {
        return customerRepository.findById(id).get();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }


    public void deleteById(Integer id) {
        Optional<Customer> deletedCustomer = customerRepository.findById(id);
        if (deletedCustomer.isPresent()) {
            customerRepository.deleteById(id);
        }
    }
}
