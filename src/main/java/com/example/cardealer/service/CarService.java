package com.example.cardealer.service;

import com.example.cardealer.mappers.CarMapper;
import com.example.cardealer.mappers.CustomerMapper;
import com.example.cardealer.mappers.OwnerMapper;
import com.example.cardealer.model.*;
import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.dtos.CustomerDto;
import com.example.cardealer.model.dtos.OwnerDto;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.repository.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class CarService {
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;
    private CarMapper carMapper;
    private OwnerMapper ownerMapper;
    private CustomerMapper customerMapper;
    private AgreementRepository agreementRepository;

    @Autowired
    public CarService(CarRepository carRepository,
                      OwnerRepository ownerRepository,
                      CustomerRepository customerRepository,
                      AgreementRepository agreementRepository,
                      InvoiceRepository invoiceRepository,
                      CarMapper carMapper,
                      OwnerMapper ownerMapper,
                      CustomerMapper customerMapper) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
        this.customerRepository = customerRepository;
        this.agreementRepository = agreementRepository;
        this.invoiceRepository = invoiceRepository;
        this.carMapper = carMapper;
        this.ownerMapper = ownerMapper;
        this.customerMapper = customerMapper;
    }

    public List<CarDto> getCarsDto() {
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

    public void sellingCar(Owner owner, Car car) {
        CarDto carDto = carMapper.map(car);

        Integer presentOwnerId = carDto.getOwnerId();
        Optional<Owner> presentOwner = ownerRepository.getOwnerById(presentOwnerId);
        presentOwner.ifPresent(o -> o.setStatus(Owner.Status.ABSENT));
        presentOwner.ifPresent(ownerRepository::save);

        owner.setStatus(Owner.Status.PRESENT);
        Owner saveOwnerDto = ownerRepository.save(owner);

        carDto.setId(car.getId());
        carDto.setStatus(Car.Status.SOLD);
        carDto.setPrice(car.getPrice());
        carDto.setFirstName(saveOwnerDto.getFirstName());
        carDto.setLastName(saveOwnerDto.getLastName());
        carDto.setAddress(saveOwnerDto.getAddress());
        carDto.setPesel(saveOwnerDto.getPesel());
        carDto.setTin(saveOwnerDto.getTin());
        carDto.setEmail(saveOwnerDto.getEmail());
        carDto.setPhoneNumber(saveOwnerDto.getPhoneNumber());
        carDto.setTransaction(Transaction.SALE);
        Car updateCar = carRepository.save(carMapper.reverse(carDto));

        Customer customer = new Customer();
        customer.setFirstName(owner.getFirstName());
        customer.setLastName(owner.getLastName());
        customer.setAddress(owner.getAddress());
        customer.setPesel(String.valueOf(owner.getPesel()));
        customer.setTin(String.valueOf(owner.getTin()));
        customer.setEmail(owner.getEmail());
        customer.setPhoneNumber(owner.getPhoneNumber());
        Customer saveNewCustomer = customerRepository.save(customer);

        Agreement agreement = new Agreement();
        agreement.setCar(updateCar);
        agreement.setCustomer(saveNewCustomer);
        agreement.setContent("Umowa sprzedaży samochodu. "
                +updateCar.getMark()+" "
                +updateCar.getModel()+" "
                +updateCar.getBodyNumber()+" "
                +updateCar.getRegNumber());
        agreement.setTransaction(Transaction.SALE);
        Agreement saveNewAgreement = agreementRepository.save(agreement);

        Invoice invoice = new Invoice();
        invoice.setDateOfIssue(new Date());
        invoice.setPrice(updateCar.getPrice());
        invoice.setPlaceOfIssue("Nazwa miasta");
        invoice.setTransaction(Transaction.SALE);
        invoice.setAgreements(saveNewAgreement);
        Invoice invoiceDatabase = invoiceRepository.save(invoice);
        invoice.setInvoiceNumber(getInvoiceNumber(invoiceDatabase));
        invoiceRepository.save(invoiceDatabase);
    }

    private String getInvoiceNumber(Invoice invoiceDatabase) {
        String getActualMonth = String.valueOf(LocalDate.now().getMonth().ordinal() + 1);
        String getActualYear = String.valueOf(LocalDate.now().getYear());
        String getActualTransaction = String.valueOf(invoiceDatabase.getTransaction());
        return invoiceDatabase.getId().toString()
                .concat("/")
                .concat(getActualMonth)
                .concat("/")
                .concat(getActualYear)
                .concat("/")
                .concat(getActualTransaction);
    }

    public void addingCarToOfferCommission(Integer id) {
        Car databaseCar = carRepository.findCarById(id);
        databaseCar.setStatus(Car.Status.AVAILABLE);
        Optional<Owner> databaseOwner = ownerRepository.getOwnerById(databaseCar.getOwnerId());
        CustomerDto customerDto = new CustomerDto();
        if (databaseOwner.isPresent()) {
            customerDto.setLastName(databaseOwner.get().getLastName());
            customerDto.setFirstName(databaseOwner.get().getFirstName());
            customerDto.setAddress(databaseOwner.get().getAddress());
            customerDto.setPesel(String.valueOf(databaseOwner.get().getPesel().longValue()));
            customerDto.setTin(String.valueOf(databaseOwner.get().getTin()));
            customerRepository.save(customerMapper.reverse(customerDto));
        }
        Agreement agreement = new Agreement();
        agreement.setCar(databaseCar);
        agreement.setCustomer(customerMapper.reverse(customerDto));
        agreement.setContent("Umowa odstąpienie pojazdu do komisu");
        agreement.setTransaction(Transaction.RENOUNCEMENT);
        agreementRepository.save(agreement);
        carRepository.save(databaseCar);
    }

    public List<CarDto> getAvailableCars() {
        return carRepository
                .findCarsByStatus(Car.Status.AVAILABLE)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
    }

    public List<CarDto> getAvailableCarsWithOwnerPersonalData() {
        List<CarDto> databaseCars = carRepository
                .findCarsByStatus(Car.Status.AVAILABLE)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
        addFirstAndLastNameByOwner(databaseCars);
        return databaseCars;
    }

    public List<CarDto> getWaitingCarsWithOwnerPersonalData() {
        List<CarDto> databaseCars = carRepository
                .findCarsByStatus(Car.Status.WAIT)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
        addFirstAndLastNameByOwner(databaseCars);
        return databaseCars;
    }

    public Car getCarByOwnerId(Integer ownerId) {
        return carRepository.findCarByOwnerId(ownerId);

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

    public List<CarDto> getCarsDtoAndOwnersName() {
        List<CarDto> databaseCarsDto = carRepository
                .findAll()
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
        addFirstAndLastNameByOwner(databaseCarsDto);
        return databaseCarsDto;
    }

    public List<CarDto> findCarsByStatus(Car.Status status) {
        List<CarDto> databaseCarsDto = carRepository
                .findCarsByStatus(status)
                .stream()
                .map(carMapper::map)
                .collect(Collectors.toList());
        addFirstAndLastNameByOwner(databaseCarsDto);
        return databaseCarsDto;
    }

    private void addFirstAndLastNameByOwner(List<CarDto> databaseCollection) {
        for (CarDto carDto : databaseCollection) {
            Integer ownerId = carDto.getOwnerId();
            OwnerDto databaseOwnerDto = ownerRepository
                    .getOwnerById(ownerId)
                    .map(ownerMapper::map)
                    .get();
            carDto.setFirstName(databaseOwnerDto.getFirstName());
            carDto.setLastName(databaseOwnerDto.getLastName());
        }
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
                    car.setId(carDto.getId());
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

    public CarDto getCarDto(Integer id){
        return carMapper.map(carRepository.findCarById(id));
    }

    public Integer getOwnerIdByCarId(Integer carId) {
        return carRepository.findOwnerIdByCarId(carId);
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
