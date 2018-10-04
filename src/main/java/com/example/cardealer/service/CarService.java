package com.example.cardealer.service;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }


    public Car getCar(Integer id) {
        return carRepository.findById(id).get();
    }


    @Query("select car_id from cardealer.events where transaction like :transaction")
    public List<Car> findCarByTransactionLike(@Param("transaction") Transaction transaction) {

        return carRepository.findAll();
    }


    public void save(Car car) {
        carRepository.save(car);
    }
}
