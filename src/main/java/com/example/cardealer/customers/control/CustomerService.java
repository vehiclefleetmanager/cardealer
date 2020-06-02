package com.example.cardealer.customers.control;

import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.config.TemporaryPassword;
import com.example.cardealer.customers.boundary.CustomerRepository;
import com.example.cardealer.customers.boundary.UpdateCustomerRequest;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.events.boundary.CreateCessionRequest;
import com.example.cardealer.users.boundary.RoleRepository;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemporaryPassword password;


    public Collection<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }


    public void addCarOwner(Car addedCar, CreateCessionRequest request) {
        Customer addCustomer = getCustomer(request.getFirstName(), request.getLastName(), request.getAddress(),
                request.getPhoneNumber(), request.getTin(), request.getPesel(), request.getIdNumber(),
                request.getEmail(), Customer.Status.PRESENT);
        addCustomer.addCar(addedCar);
        addedCar.setCarOwner(addCustomer);
        saveCustomer(addCustomer);
        //TODO/*
        // add send info on customer email
        // */
    }

    public void addCustomer(String firstName, String lastName, String address, String phoneNumber, String tin,
                            String pesel, String idNumber, String email) {
        saveCustomer(getCustomer(firstName, lastName, address, phoneNumber, tin,
                pesel, idNumber, email, Customer.Status.FUTURE));
        //TODO/*
        // add send info on customer email
        // */
    }

    public void updateCustomer(Long id, UpdateCustomerRequest request) {
        customerRepository.findById(id).ifPresent(
                c -> {
                    c.setFirstName(request.getFirstName());
                    c.setLastName(request.getLastName());
                    c.setPhoneNumber(request.getPhoneNumber());
                    c.setAddress(request.getAddress());
                    c.setTin(request.getTin());
                    c.setPesel(request.getPesel());
                    c.setIdNumber(request.getIdNumber());
                    c.setEmail(request.getEmail());
                    customerRepository.save(c);
                });
    }

    public void deleteCustomer(Long id) {
        customerRepository.findById(id).ifPresent(
                customer -> customerRepository.deleteById(id));
    }

    public List<Customer> getCustomersFromSearchButton(String firstName, String lastName,
                                                       String pesel, String tin) {
        return customerRepository.findCustomerFromSearchButton(firstName, lastName, pesel, tin);
    }

    public Page<Customer> getCustomersFromSearchButton(String firstName, String lastName,
                                                       String pesel, String tin, Pageable pageable) {
        return customerRepository.findCustomerFromSearchButton(firstName, lastName, pesel, tin, pageable);
    }


    private Customer getCustomer(String firstName, String lastName, String address,
                                 String phoneNumber, String tin, String pesel,
                                 String idNumber, String email, Customer.Status status) {
        return new Customer(firstName, lastName, address, phoneNumber,
                tin, pesel, idNumber, email, status);
    }

    private void saveCustomer(Customer addCustomer) {
        Customer newCustomer = customerRepository.save(addCustomer);
        Role customerRole = roleRepository.findByName("Client".toUpperCase());
        String tempPass = password.temporaryPassword();
        System.out.println("Temporary password for " + newCustomer.getFirstName() + " set: " + tempPass);
        User addUser = new User(newCustomer, true, passwordEncoder.encode(tempPass));
        addUser.addRole(customerRole);
        userRepository.save(addUser);
    }
}
