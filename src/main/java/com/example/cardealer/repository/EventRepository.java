package com.example.cardealer.repository;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
import com.example.cardealer.model.enums.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("select e.car from Event e where e.transaction = :transaction")
    List<Car> findByTransaction(@Param("transaction") Transaction transaction);

    @Query("select e.car from Event e where e.transaction = 1")
    List<Car> findByRenouncement(@Param("transaction") Transaction transaction);

    @Query("select e.customer from Event e where e.car.id = :id")
    Customer findCustomerByCarId(@Param("id") Integer integer);

    @Query("select e from Event e where e.car.id = :id")
    Event findEventByCarId(@Param("id") Integer integer);

}
