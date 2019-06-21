package com.example.cardealer.repository;

import com.example.cardealer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where c.tin = ?1")
    Optional<Customer> findCustomerByTinNumber(Integer tinNumber);

    @Query("select c from Customer c where c.pesel =?1")
    Optional<Customer> findCustomerByPeselNumber(String peselNumber);

    @Query("select cust from " +
            "Customer cust, Car car " +
            "join cust.cars ca " +
            "join car.customers cu " +
            "where ca.id = car.id " +
            "and cust.id = cu.id")
    List<Customer> findCustomersByInterestedCar();

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.pesel = ?1")
    void deleteByPeselNumber(String peselNumber);

}
