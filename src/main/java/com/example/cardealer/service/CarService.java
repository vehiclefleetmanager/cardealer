package com.example.cardealer.service;

import com.example.cardealer.mappers.CarMapper;
import com.example.cardealer.mappers.OwnerMapper;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.repository.CarRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class CarService {
    private CarRepository carRepository;
    /* private final EventRepository eventRepository;*/
    private CarMapper carMapper;
    private OwnerMapper ownerMapper;

    @Autowired
    public CarService(CarRepository carRepository,
            /*EventRepository eventRepository,*/
                      CarMapper carMapper,
                      OwnerMapper ownerMapper) {
        this.carRepository = carRepository;
        /* this.eventRepository = eventRepository;*/
        this.carMapper = carMapper;
        this.ownerMapper = ownerMapper;
    }

    public List<CarDto> getCars() {
        return carRepository
                .findAll()
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarDto> getCarsFromSearchButton(String carMark, String carModel,
                                                Integer fromYear, Integer toYear,
                                                BigDecimal fromPrice, BigDecimal toPrice, Car.Status status) {
        return carRepository.findCarsFromSearchButton(carMark,
                carModel, fromYear, toYear, fromPrice, toPrice, status)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarDto> getAvailableCars() {
        return carRepository
                .findCarsByStatusIsAvailable(Car.Status.AVAILABLE)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarDto> getCarsDtoByMark(String mark) {
        return carRepository
                .findCarsByMark(mark)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarDto> getCarsDtoByModel(String model) {
        return carRepository
                .findCarsByMark(model)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarDto> getCarsByOwnersIds(Integer ownerId) {
        return carRepository
                .findCarsByOwnerId(ownerId)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public CarDto getCarFindByRegNumber(String regNumber) {
        return carRepository.findCarByRegNumber(regNumber)
                .map(carMapper::map)
                .get();
    }

    public CarDto getCarFindByBodyNumber(String bodyNumber) {
        return carRepository.findCarByRegNumber(bodyNumber)
                .map(carMapper::map)
                .get();
    }

    public Car addCar(CarDto carDto) {
        return carRepository.save(carMapper.reverse(carDto));
    }

    public void updateCar(CarDto carDto) {
        carRepository.findCarByRegNumber(carDto.getRegNumber())
                .ifPresent(car -> {
                    car.setModel(carDto.getModel());
                    car.setMark(carDto.getMark());
                    car.setBodyNumber(carDto.getBodyNumber());
                    car.setCapacityEngine(carDto.getCapacityEngine());
                    car.setDescription(carDto.getDescription());
                    car.setTestDrive(carDto.getTestDrive());
                    car.setDistance(carDto.getDistance());
                    car.setFuelType(carDto.getFuelType());
                    car.setOcNumber(carDto.getOcNumber());
                    car.setPowerEngine(carDto.getPowerEngine());
                    car.setPrice(carDto.getPrice());
                    car.setProductionYear(carDto.getProductionYear());
                    car.setRegNumber(carDto.getRegNumber());
                    car.setStatus(carDto.getStatus());
                    car.setTransmission(carDto.getTransmission());
                    car.setOwnerId(carDto.getOwnerId());
                    carRepository.save(car);
                });
    }

    public void deleteCarByRegNumber(String regNumber) {
        carRepository.deleteByRegNumber(regNumber);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }


    public Car getCar(Integer id) {
        return carRepository.findById(id).get();
    }

   /* public List<Car> findCarByTransactionLike(Transaction transaction) {
        return eventRepository.findByTransaction(transaction);
    }

    public List<Car> findByRenouncementLike(Transaction transaction) {
        return eventRepository.findByRenouncement(transaction);
    }*/

    /*  public List<Car> findByWaitingLike(Transaction transaction) {
          return eventRepository.findByWaiting(transaction);
      }
  */

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
