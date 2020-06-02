package com.example.cardealer.customers.boundary;

import com.example.cardealer.customers.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.firstName = ?1 or c.lastName = ?2 or c.pesel = ?3 or c.tin = ?4")
    List<Customer> findCustomerFromSearchButton(String firstName, String lastName, String pesel, String tin);

    @Query("select c from Customer c where c.firstName = ?1 or c.lastName = ?2 or c.pesel = ?3 or c.tin = ?4")
    Page<Customer> findCustomerFromSearchButton(String firstName, String lastName, String pesel, String tin, Pageable pageable);
}
