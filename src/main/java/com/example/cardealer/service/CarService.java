package com.example.cardealer.service;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.repository.CarRepository;
import com.example.cardealer.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CarService(CarRepository carRepository, EventRepository eventRepository) {
        this.carRepository = carRepository;
        this.eventRepository = eventRepository;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }


    public Car getCar(Integer id) {
        return carRepository.findById(id).get();
    }


    public List<Car> findCarByTransactionLike(Transaction transaction) {
        return eventRepository.findByTransaction(transaction);
    }

    public List<Car> findByRenouncementLike(Transaction transaction) {
        return eventRepository.findByRenouncement(transaction);
    }


    public void save(Car car) {
        carRepository.save(car);
    }


    public void deleteById(Integer id) {
        Optional<Car> deletedCar = carRepository.findById(id);
        if (deletedCar.isPresent()) {
            carRepository.deleteById(id);
        }
    }

    public List<Car> findCarByMark(String mark) {
        return carRepository.findCarsByMark(mark);
    }

    public List<Car> findCarsByModel(String model) {
        return carRepository.findCarsByModel(model);
    }

    public List<String> findMark() {
        return carRepository.findMark();
    }

    public List<String> findModel() {
        return carRepository.findModel();
    }

    public List<Integer> findProductionYear() {
        return carRepository.findProductionYear();
    }

    public void updateTestDrive(Car car) {
        car.setTestDrive(car.getTestDrive() + 1);
    }
}
