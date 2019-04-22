package com.example.cardealer.service;

import com.example.cardealer.mappers.CustomerMapper;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.dtos.CustomerDto;
import com.example.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getCustomersDto() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::map)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerDtoByTinNumber(Integer tinNumber) {
        return customerRepository
                .findCustomerByTinNumber(tinNumber)
                .map(customerMapper::map)
                .get();
    }

    public CustomerDto getCustomerDtoByPesel(String peselNumber) {
        return customerRepository
                .findCustomerByPeselNumber(peselNumber)
                .map(customerMapper::map)
                .get();
    }

    public Customer addCustomer(CustomerDto customerDto) {
        return customerRepository.save(customerMapper.reverse(customerDto));
    }

    public void updateCustomer(CustomerDto customerDto) {
        customerRepository.findCustomerByPeselNumber(customerDto.getPesel())
                .ifPresent(c -> {
                    c.setPesel(customerDto.getPesel());
                    c.setTin(customerDto.getTin());
                    c.setAddress(customerDto.getAddress());
                    c.setFirstName(customerDto.getFirstName());
                    c.setLastName(customerDto.getLastName());
                    c.setPhoneNumber(customerDto.getPhoneNumber());
                    customerRepository.save(c);
                });
    }

    public void deleteCustomerByPeselNumber(String peselNumber) {
        customerRepository.deleteByPeselNumber(peselNumber);
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
