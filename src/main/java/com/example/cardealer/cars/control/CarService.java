package com.example.cardealer.cars.control;

import com.example.cardealer.cars.boundary.CarRepository;
import com.example.cardealer.cars.boundary.UpdateCarRequest;
import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.config.Clock;
import com.example.cardealer.config.SystemGenerateNumbers;
import com.example.cardealer.customers.boundary.CustomerRepository;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.documents.boundary.AgreementRepository;
import com.example.cardealer.documents.boundary.InvoiceRepository;
import com.example.cardealer.documents.entiity.Agreement;
import com.example.cardealer.documents.entiity.Invoice;
import com.example.cardealer.employees.boundary.EmployeeRepository;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.events.boundary.*;
import com.example.cardealer.events.control.CessionService;
import com.example.cardealer.events.entity.*;
import com.example.cardealer.repairs.boundary.RepairRepository;
import com.example.cardealer.repairs.entity.Repair;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.User;
import com.example.cardealer.utils.enums.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.cardealer.cars.entity.Car.*;


@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final RepairRepository repairRepository;
    private final InvoiceRepository invoiceRepository;
    private final AgreementRepository agreementRepository;
    private final CessionService cessionService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final TestDriveRepository testDriveRepository;
    private final SystemGenerateNumbers generateNumbers;
    private final Clock clock;

    public Page<Car> findAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Page<Car> findAllAvailableCars(Pageable pageable) {
        return carRepository.findCarsByStatus(pageable, Status.AVAILABLE);
    }

    public Page<Car> findAllCarsOfUser(Long userId, Pageable pageable) {
        return carRepository.findAllCarsOfCustomer(userId, pageable);
    }

    public void addCarAndCreateCessionEventAndCessionAgreement(CreateCessionRequest request, Long employeeId) {
        //add new car's owner
        Customer existOwner = createCarOwner(request);
        // add new car
        Car car = createNewCar(request, existOwner);
        existOwner.addCar(car);
        //create cession event
        makeCession(car, existOwner, clock.date());
    }

    private void makeCession(Car car, Customer existOwner, LocalDate date) {
        // this variable "count" will greater by 0 Car shouldn't add to system
        long count = carRepository.findCarsByStatus(Status.SOLD).stream()
                .filter(c -> c.getBodyNumber().matches(car.getBodyNumber())).count();
        //this boolean variable "checkCarStatus" set true when car's status is 'WAIT'
        // or false when car's status is different
        boolean checkCarStatus = car.getStatus().name().matches(Status.WAIT.name());
        if (count == 0L && checkCarStatus) {
            car.setStatus(Status.ACCEPTED);
            Cession cession = new Cession(car, existOwner, date);
            Cession newCession = cessionService.save(cession);
            makeAgreement(existOwner, newCession, Transaction.CESSION);
        }
    }

    @Transactional
    public void updateCar(Long carId, UpdateCarRequest request) {

        carRepository.findById(carId)
                .map(car -> updateFields(request, car));

        /*findCar(carId).setBodyNumber(request.getBodyNumber());
        findCar(carId).setBodyType(BodyType.findByName(request.getBodyType().toUpperCase()));
        findCar(carId).setCapacityEngine(request.getCapacityEngine());
        findCar(carId).setDescription(request.getDescription());
        findCar(carId).setDistance(request.getDistance());
        findCar(carId).setFuelType(FuelType.findByName(request.getFuelType().toUpperCase()));
        findCar(carId).setMark(request.getMark());
        findCar(carId).setModel(request.getModel());
        findCar(carId).setOcNumber(request.getOcNumber());
        findCar(carId).setPowerEngine(request.getPowerEngine());
        findCar(carId).setProductionYear(request.getProductionYear());
        findCar(carId).setTransmission(Transmission.findByName(request.getTransmission().toUpperCase()));
        carRepository.save(findCar(carId));*/
    }

    private Car updateFields(UpdateCarRequest request, Car car) {
        if (request.getMark() != null) {
            car.setMark(request.getMark());
        }
        if (request.getModel() != null) {
            car.setModel(request.getModel());
        }
        if (request.getBodyNumber() != null) {
            car.setBodyNumber(request.getBodyNumber());
        }
        if (request.getProductionYear() != null) {
            car.setProductionYear(request.getProductionYear());
        }
        if (request.getOcNumber() != null) {
            car.setOcNumber(request.getOcNumber());
        }
        if (request.getFuelType() != null) {
            car.setFuelType(FuelType.findByName(request.getFuelType()));
        }
        if (request.getDistance() != null) {
            car.setDistance(request.getDistance());
        }
        if (request.getBodyType() != null) {
            car.setBodyType(BodyType.findByName(request.getBodyType()));
        }
        if (request.getCapacityEngine() != null) {
            car.setCapacityEngine(request.getCapacityEngine());
        }
        if (request.getPowerEngine() != null) {
            car.setPowerEngine(request.getPowerEngine());
        }
        if (request.getTransmission() != null) {
            car.setTransmission(Transmission.findByName(request.getTransmission()));
        }
        if (request.getDescription() != null) {
            car.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            car.setPrice(new BigDecimal(request.getPrice()));
        }
        return car;
    }

    public void repairCar(Long carId, CreateRepairRequest request, Long employeeId) {
        Car car = findCar(carId);
        Employee employee = findEmployee(employeeId);
        //Repair
        Repair repair = new Repair();
        repair.setRepairDate(clock.date());
        repair.setRepairAmount(new BigDecimal(request.getRepairAmount()));
        repair.setEmployee(employee);
        repair.setRepairDescription(request.getRepairDescription());
        repair.setCar(car);
        Repair newRepair = repairRepository.save(repair);
        //Update car's price status and added repair to car
        car.setPrice(car.getPrice().add(newRepair.getRepairAmount()));
        repair.setCar(car);
        car.setStatus(Status.AVAILABLE);
        car.addRepair(newRepair);
        carRepository.save(car);
    }

    public boolean saleCar(CreateCarSaleRequest request, Long employeeId) {
        Car car = findCar(request.getCarId());
        Customer existOwner = car.getCarOwner();
        Customer newOwner = customerRepository.getOne(request.getNewOwnerId());
        Employee employee = findEmployee(employeeId);
        boolean isAvailable = car.getStatus().name().matches(Status.AVAILABLE.name());
        boolean isInnerOwner = existOwner.getId().toString().matches(newOwner.getId().toString());
        if (isAvailable && !isInnerOwner) {
            Invoice invoicePurchase = preparePurchaseEvent(car, existOwner, employee);
            prepareSaleEvent(invoicePurchase, newOwner);
            return true;
        }
        return false;
    }

    private Invoice preparePurchaseEvent(Car car, Customer customer, Employee employee) {
        BigDecimal priceOfCar = prepareAmountOfCarForPurchaseEvent(car);
        Purchase purchase = new Purchase();
        purchase.setEventDate(clock.date());
        purchase.setPurchaseAmount(priceOfCar);
        purchase.setCustomer(customer);
        purchase.setCar(car);
        Agreement agreementPurchase = makeAgreement(car.getCarOwner(), purchase, Transaction.PURCHASE);
        agreementPurchase.setAgreementAmount(priceOfCar);
        purchase.setAgreement(agreementPurchase);
        Invoice purchaseInvoice = makeInvoice(purchase, agreementPurchase, Transaction.PURCHASE);
        purchaseInvoice.setInvoiceAmount(priceOfCar);
        purchaseInvoice.setEmployee(employee);
        customer.removeCar(car);
        car.setStatus(Status.BOUGHT);
        return invoiceRepository.save(purchaseInvoice);
    }

    private void prepareSaleEvent(Invoice invoice, Customer customer) {
        Car car = invoice.getAgreement().getEvent().getCar();
        customer.addCar(car);
        car.setCarOwner(customer);
        car.setStatus(Status.SOLD);
        Sale sale = new Sale();
        sale.setEventDate(clock.date());
        sale.setSaleAmount(car.getPrice());
        sale.setCar(car);
        sale.setCustomer(customer);
        Agreement agreement = makeAgreement(customer, sale, Transaction.SALE);
        agreement.setAgreementAmount(sale.getSaleAmount());
        sale.setAgreement(agreement);
        Invoice saleInvoice = makeInvoice(sale, agreement, Transaction.SALE);
        saleInvoice.setInvoiceAmount(agreement.getAgreementAmount());
        saleInvoice.setEmployee(invoice.getEmployee());
        invoiceRepository.save(saleInvoice);
    }

    private Agreement makeAgreement(Customer customer, Event event, Transaction transaction) {
        Agreement agreement = new Agreement();
        agreement.setEvent(event);
        agreement.setCustomer(customer);
        agreement.setAgreementNumber(generateNumbers.
                generateAgreementsNumbers(agreementRepository.findAll(), event));
        agreement.setContent(
                event.getCar().getMark() + " " + event.getCar().getModel() + " " +
                        event.getCar().getBodyNumber());
        agreement.setTransaction(transaction);
        agreement.setCreatedAt(clock.date());
        agreement.setEvent(event);
        return agreement;
    }

    private Invoice makeInvoice(Event event, Agreement agreement, Transaction transaction) {
        Invoice invoice = new Invoice();
        invoice.setAgreement(event.getAgreement());
        invoice.setCustomer(event.getCustomer());
        invoice.setCreatedAt(clock.date());
        invoice.setInvoiceAmount(agreement.getAgreementAmount());
        invoice.setTransaction(transaction);
        invoice.setInvoiceNumber(generateNumbers.generateInvoicesNumbers(
                invoiceRepository.findAll(), event));
        event.getAgreement().setInvoice(invoice);
        return invoice;
    }

    private BigDecimal prepareAmountOfCarForPurchaseEvent(Car car) {
        BigDecimal repairsValue = new BigDecimal(String.valueOf(BigDecimal.ZERO));
        if (!car.getRepairs().isEmpty()) {
            car.getRepairs().forEach(
                    repair -> repairsValue.add(repair.getRepairAmount()));
        }
        BigDecimal priceCarSubtractRepairs = car.getPrice().subtract(repairsValue);
        return priceCarSubtractRepairs.multiply(new BigDecimal("0.80"));
    }

    public void deleteCar(Long id, User userSystem) {
        if (userSystem.getRoles().stream().anyMatch(r -> r.getName().matches("ADMIN"))
                || userSystem.getRoles().stream().anyMatch(r -> r.getName().matches("WORKER"))
                || userSystem.getRoles().stream().anyMatch(r -> r.getName().matches("CLIENT"))) {
            carRepository.findById(id).ifPresent(
                    car -> {
                        car.setStatus(Status.DELETED);
                        carRepository.save(car);
                    });
        }
    }

    public String testDriveReservation(CreateTestDriveRequest request) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = LocalDate.parse(request.getDate(), dateFormatter);
        LocalTime time = LocalTime.parse(request.getTime(), timeFormatter);
        Car testedCar = findCar(request.getCarId());
        Set<TestDrive> testDrives = testedCar.getTestDrives();
        String flag = "ok";
        boolean checkTime = true;
        if (testDrives.isEmpty()) {
            createTestDrive(request, date, time, testedCar);
        } else {
            for (TestDrive dateVarTestDrive : testDrives) {
                if (dateVarTestDrive.getDateOfTestDrive().isEqual(date)) {
                    for (TestDrive timeVarTestDrive : testDrives) {
                        if (timeVarTestDrive.getTimeOfTestDrive().equals(time)) {
                            checkTime = false;
                            flag = "time";
                            break;
                        }
                    }
                }
                /*if day is not equal date of request, create reservation this car*/
                else {
                    createTestDrive(request, date, time, testedCar);
                    break;
                }
            }
            if (checkTime) {
                createTestDrive(request, date, time, testedCar);
            }
        }
        //TODO
        /*sent emails*/
        return flag;
    }


    public List<Car> getCarsFromSearchButton(String carMark, String maxYear,
                                             String maxPrice) {
        int maxYearValue = 0;
        BigDecimal maxPriceValue = BigDecimal.ZERO;
        if (!maxYear.isEmpty()) {
            maxYearValue = Integer.parseInt(maxYear);
        }
        if (!maxPrice.isEmpty()) {
            maxPriceValue = BigDecimal.valueOf(Long.parseLong(maxPrice));
        }
        return carRepository.findCarsFromSearchButton(carMark,
                maxYearValue, maxPriceValue)
                .stream()
                .filter(car -> car.getStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList());
    }

    public Page<Car> getCarsFromSearchButton(String carMark, String maxYear, String maxPrice, String status, Pageable pageable) {
        int maxYearValue = 0;
        Status searchStatus = Status.AVAILABLE;
        BigDecimal maxPriceValue = BigDecimal.ZERO;
        if (!maxYear.isEmpty()) {
            maxYearValue = Integer.parseInt(maxYear);
        }
        if (!maxPrice.isEmpty()) {
            maxPriceValue = BigDecimal.valueOf(Long.parseLong(maxPrice));
        }
        if (!status.isEmpty()) {
            searchStatus = getCarStatus(status);
        }
        return carRepository.findCarsFromSearchButton(carMark,
                maxYearValue, maxPriceValue, searchStatus, pageable);

    }

    private BigDecimal getRepairsAmount(Collection<Repair> allRepairs) {
        BigDecimal repairAmount = new BigDecimal("0");
        if (!allRepairs.isEmpty()) {
            BigDecimal temp = new BigDecimal("0");
            for (Repair r : allRepairs) {
                temp = temp.add(r.getRepairAmount());
            }
            repairAmount = temp;
        }
        return repairAmount;
    }

    private void createTestDrive(CreateTestDriveRequest request, LocalDate date, LocalTime time, Car testedCar) {
        User testedUser = findUser(request.getUserId());
        TestDrive testDrive = new TestDrive();
        testDrive.setCar(testedCar);
        testDrive.setUser(testedUser);
        testDrive.setDateOfTestDrive(date);
        testDrive.setTimeOfTestDrive(time);
        testDrive.setStatus(TestDrive.Status.NEW);
        TestDrive newTestDrive = testDriveRepository.save(testDrive);
        testedCar.addTestDrive(newTestDrive);
        carRepository.save(testedCar);
    }

    public void updateTestDriveReservation(Long id, UpdateTestDriveRequest request) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = LocalDate.parse(request.getDate(), dateFormatter);
        LocalTime time = LocalTime.parse(request.getTime(), timeFormatter);
        TestDrive testDrive = findTestDrive(id);
        int drive = testDrive.getCar().getTestDrive();
        testDrive.getCar().setTestDrive(drive + 1);
        testDrive.setDateOfTestDrive(date);
        testDrive.setTimeOfTestDrive(time);
        testDrive.setStatus(TestDrive.Status.ACCEPT);
        testDriveRepository.save(testDrive);
    }

    public List<String> findProductionYear() {
        return carRepository.findProductionYear(Status.AVAILABLE).stream()
                .map(Object::toString).collect(Collectors.toList());
    }

    private Car createNewCar(CreateCessionRequest request, Customer existOwner) {
        BodyType body = BodyType.findByName(request.getBodyType());
        FuelType fuel = FuelType.findByName(request.getFuelType());
        Transmission transmission = Transmission.findByName(request.getTransmission());
        Car car = new Car(request.getBodyNumber(), request.getProductionYear(), request.getMark(),
                request.getModel(), request.getOcNumber(), fuel, request.getDistance(), body,
                request.getCapacityEngine(), request.getPowerEngine(), transmission, request.getDescription(),
                request.getPrice());
        car.setStatus(Status.WAIT);
        car.setCarOwner(existOwner);
        return car;
    }

    private Customer createCarOwner(CreateCessionRequest request) {
        return new Customer(request.getFirstName(), request.getLastName(), request.getAddress(),
                request.getPhoneNumber(), request.getTin(), request.getPesel(), request.getIdNumber(),
                request.getEmail(), Customer.Status.PRESENT);
    }

    private TestDrive findTestDrive(Long id) {
        return testDriveRepository.getOne(id);
    }

    public Car findCar(Long id) {
        return carRepository.getOne(id);
    }

    private User findUser(Long userId) {
        return userRepository.getOne(userId);
    }

    private Employee findEmployee(Long workerId) {
        return employeeRepository.getOne(workerId);
    }

    public List<String> findMark() {
        return carRepository.findMark(Status.AVAILABLE);
    }

    private Status getCarStatus(String status) {
        return Status.valueOf(status);
    }

    private String getBasicInfoAboutCar(Car updateCar) {
        return updateCar.getMark() + " " + updateCar.getModel()
                + " " + updateCar.getBodyNumber();
    }
}