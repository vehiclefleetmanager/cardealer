package com.example.cardealer.customers.entity;

import com.example.cardealer.cars.entity.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {

    @Test
    public void shouldAddCar() {
        //given
        Customer customer = new Customer();
        Car addedCar = new Car();

        //when
        customer.addCar(addedCar);

        //then
        assertTrue(customer.getCars().contains(addedCar));
    }

    @Test
    public void testRemoveCar() {
        //given
        Customer customer = new Customer();
        Car addedCar = new Car();
        customer.addCar(addedCar);

        //when
        customer.removeCar(addedCar);

        //then
        assertTrue(customer.getCars().isEmpty());
    }
}