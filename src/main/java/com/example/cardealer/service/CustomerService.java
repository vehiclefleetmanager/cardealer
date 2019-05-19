package com.example.cardealer.service;

import com.example.cardealer.mappers.CarMapper;
import com.example.cardealer.mappers.CustomerMapper;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.CustomerDto;
import com.example.cardealer.repository.CarRepository;
import com.example.cardealer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private final CarRepository carRepository;
    private final CarMapper carMapper;


    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper,
                           CarRepository carRepository,
                           CarMapper carMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public List<CustomerDto> getCustomersDto() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::map)
                .collect(Collectors.toList());
    }

    public List<CustomerDto> getCustomerDtoBodyNumberMarkAndModelOfCheckedCar() {
        List<CustomerDto> databaseCustomerDto = customerRepository.findAll()
                .stream()
                .map(customerMapper::map)
                .collect(Collectors.toList());
        addBodyNumberCarModelAndMarkToResultList(databaseCustomerDto);
        return databaseCustomerDto;
    }

    private void addBodyNumberCarModelAndMarkToResultList(List<CustomerDto> databaseCollection) {
        Set<CarDto> databaseCarsDto;
        for (CustomerDto customerDto : databaseCollection) {
            Integer customerId = customerDto.getId();
            databaseCarsDto = carRepository
                    .findCarsByCustomerId(customerId)
                    .stream()
                    .map(carMapper::map)
                    .collect(Collectors.toSet());
            //customerDto.setCars(databaseCarsDto);
            if (databaseCarsDto.iterator().hasNext()) {
                customerDto.setBodyNumber(databaseCarsDto.iterator().next().getBodyNumber());
                customerDto.setCarMark(databaseCarsDto.iterator().next().getMark());
                customerDto.setCarModel(databaseCarsDto.iterator().next().getModel());
            }
        }
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
        customerRepository.findById(customerDto.getId())
                .ifPresent(c -> {
                    c.setId(customerDto.getId());
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
