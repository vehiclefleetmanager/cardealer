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
import com.example.cardealer.events.entity.Cession;
import com.example.cardealer.events.entity.Purchase;
import com.example.cardealer.events.entity.Sale;
import com.example.cardealer.events.entity.TestDrive;
import com.example.cardealer.repairs.boundary.RepairRepository;
import com.example.cardealer.repairs.entity.Repair;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.User;
import com.example.cardealer.utils.enums.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final RepairRepository repairRepository;
    private final InvoiceRepository invoiceRepository;
    private final AgreementRepository agreementRepository;
    private final CessionService cessionService;
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private final TestDriveRepository testDriveRepository;
    private final SystemGenerateNumbers generateNumbers;
    private final Clock clock;

    public Page<Car> findAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Page<Car> findAllAvailableCars(Pageable pageable) {
        return carRepository.findCarsByStatus(pageable, Car.Status.AVAILABLE);
    }

    public void addCarAndCreateCessionEvent(CreateCessionRequest request, Long employeeId) {
        Customer existOwner = new Customer(request.getFirstName(), request.getLastName(), request.getAddress(),
                request.getPhoneNumber(), request.getTin(), request.getPesel(), request.getIdNumber(),
                request.getEmail(), Customer.Status.PRESENT);

        Car.BodyType body = Car.BodyType.findByName(request.getBodyType());
        Car.FuelType fuel = Car.FuelType.findByName(request.getFuelType());
        Car.Transmission transmission = Car.Transmission.findByName(request.getTransmission());
        Car car = new Car(request.getBodyNumber(), request.getProductionYear(), request.getMark(),
                request.getModel(), request.getOcNumber(), fuel, request.getDistance(), body,
                request.getCapacityEngine(), request.getPowerEngine(), transmission, request.getDescription(),
                request.getPrice());
        car.setStatus(Car.Status.WAIT);
        car.setCarOwner(existOwner);
        existOwner.addCar(car);
        cessionCar(car, car.getPrice(), employeeId);
    }

    public void cessionCar(Car car, BigDecimal price, Long employeeId) {
        Employee employee = findEmployee(employeeId);
        long count = carRepository.findCarsByStatus(Car.Status.SOLD).stream()
                .filter(c -> c.getBodyNumber().matches(car.getBodyNumber())).count();
        boolean checkCarStatus = car.getStatus().name().matches(Car.Status.WAIT.name());
        if (count == 0L && checkCarStatus) {
            car.setPrice(price);
            car.setStatus(Car.Status.ACCEPTED);
            Car updateCar = carRepository.save(car);
            Customer existOwner = updateCar.getCarOwner();
            makeCessionCar(existOwner, updateCar, employee);
        }
    }

    private void makeCessionCar(Customer existOwner, Car updateCar, Employee employee) {
        //AgreementCession
        Agreement newAgreement = makeAgreement("Przyjęcie w komis", Transaction.CESSION);
        //Cession
        makeCessionEvent(existOwner, updateCar, newAgreement, employee);
    }

    private void makeCessionEvent(Customer existOwner, Car updateCar, Agreement agreement, Employee employee) {
        cessionService.save(new Cession(updateCar, clock.date(), existOwner, agreement, employee));
    }

    public void updateCar(Long carId, UpdateCarRequest request) {
        findCar(carId).setBodyNumber(request.getBodyNumber());
        findCar(carId).setBodyType(Car.BodyType.findByName(request.getBodyType().toUpperCase()));
        findCar(carId).setCapacityEngine(request.getCapacityEngine());
        findCar(carId).setDescription(request.getDescription());
        findCar(carId).setDistance(request.getDistance());
        findCar(carId).setFuelType(Car.FuelType.findByName(request.getFuelType().toUpperCase()));
        findCar(carId).setMark(request.getMark());
        findCar(carId).setModel(request.getModel());
        findCar(carId).setOcNumber(request.getOcNumber());
        findCar(carId).setPowerEngine(request.getPowerEngine());
        findCar(carId).setProductionYear(request.getProductionYear());
        findCar(carId).setTransmission(Car.Transmission.findByName(request.getTransmission().toUpperCase()));
        carRepository.save(findCar(carId));
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
        car.setStatus(Car.Status.AVAILABLE);
        car.addRepair(newRepair);
        carRepository.save(car);
        //Invoice -> makeRepairCar()
        makeRepairInvoice(employee, newRepair);
    }

    private void makeRepairInvoice(Employee employee, Repair newRepair) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceAmount(newRepair.getRepairAmount());
        invoice.setEmployee(employee);
        invoice.setCreatedAt(clock.date());
        invoice.setTransaction(Transaction.REPAIR);
        invoice.setInvoiceNumber(generateNumbers.generateInvoicesNumbers(
                invoiceRepository.findAll(), Transaction.REPAIR));
        newRepair.setRepairInvoice(invoice);
        invoiceRepository.save(invoice);
    }

    public void saleCar(CreateCarSaleRequest request, Long employeeId) {
        Car car = findCar(request.getCarId());
        Customer existOwner = car.getCarOwner();
        Customer newOwner = customerRepository.getOne(request.getNewOwnerId());
        Employee employee = findEmployee(employeeId);
        boolean isAvailable = car.getStatus().name().matches(Car.Status.AVAILABLE.name());
        boolean isInnerOwner = existOwner.getId().toString().matches(newOwner.getId().toString());
        if (isAvailable && !isInnerOwner) {
            existOwner.removeCar(car);
            newOwner.addCar(car);
            car.setStatus(Car.Status.SOLD);
            Car updateCar = carRepository.save(car);
            updateCar.setCarOwner(newOwner);
            Customer updateExistOwner = customerRepository.save(existOwner);
            Customer updateNewOwner = customerRepository.save(newOwner);
            makePurchaseCar(updateExistOwner, updateCar, request.getPrice(), employee, "Zakup samochodu");
            makeSaleCar(updateNewOwner, updateCar, request.getPrice(), employee, "Sprzedaż samochodu");
        }
    }

    private void makePurchaseCar(Customer oldOwner, Car updateCar, BigDecimal price, Employee employee, String agreementContent) {
        //AgreementPurchase
        Agreement newAgreement = makeAgreement(agreementContent, Transaction.PURCHASE);
        //Purchase
        Purchase newPurchase = makePurchaseEvent(oldOwner, updateCar, newAgreement, price, employee);
        //InvoicePurchase
        makePurchaseInvoice(newAgreement, newPurchase, employee);
    }

    private Purchase makePurchaseEvent(Customer oldOwner, Car updateCar, Agreement newAgreement,
                                       BigDecimal price, Employee employee) {
        BigDecimal repairsAmount = getRepairsAmount(updateCar.getRepairs());
        BigDecimal amountWithoutRepairs = price.subtract(repairsAmount);
        BigDecimal purchasePrice = amountWithoutRepairs.multiply(new BigDecimal("0.80"));
        return purchaseRepository.save(new Purchase(updateCar, oldOwner, clock.date(), newAgreement,
                purchasePrice, employee));
    }

    private void makePurchaseInvoice(Agreement agreement, Purchase purchase, Employee employee) {
        Invoice invoice = new Invoice(agreement,
                agreement.getTransaction(),
                clock.date(), purchase.getPurchaseAmount());
        invoice.setInvoiceNumber(generateNumbers.generateInvoicesNumbers(invoiceRepository.findAll(),
                agreement.getTransaction()));
        invoice.setAgreement(agreement);
        agreement.setInvoice(invoice);
        invoice.setEmployee(employee);
        invoiceRepository.save(invoice);
    }

    private void makeSaleCar(Customer newOwner, Car updateCar, BigDecimal price,
                             Employee employee, String agreementContent) {
        //SaleAgreement
        Agreement agreement = makeAgreement(agreementContent, Transaction.SALE);
        //Sale
        Sale newSale = makeSaleEvent(newOwner, updateCar, agreement, price, employee);
        //InvoiceSale
        makeSaleInvoice(agreement, newSale, employee);
    }

    private void makeSaleInvoice(Agreement agreement, Sale sale, Employee employee) {
        Invoice invoice = new Invoice(agreement, agreement.getTransaction(), clock.date(),
                sale.getSaleAmount());
        invoice.setInvoiceNumber(generateNumbers.generateInvoicesNumbers(invoiceRepository.findAll(),
                agreement.getTransaction()));
        invoice.setEmployee(employee);
        invoice.setAgreement(agreement);
        agreement.setInvoice(invoice);
        invoiceRepository.save(invoice);
    }

    private Sale makeSaleEvent(Customer newOwner, Car updateCar, Agreement agreement,
                               BigDecimal price, Employee employee) {
        return saleRepository.save(new Sale(updateCar, newOwner, clock.date(),
                agreement, price, employee));
    }

    private Agreement makeAgreement(String content, Transaction transaction) {
        Agreement agreement = new Agreement(clock.date(), content, transaction);
        agreement.setAgreementNumber(generateNumbers.generateAgreementsNumbers(agreementRepository.findAll(),
                agreement.getTransaction()));
        return agreementRepository.save(agreement);
    }

    public void deleteCar(Long id, User userSystem) {
        if (userSystem.getRoles().stream().anyMatch(r -> r.getName().matches("ADMIN"))
                || userSystem.getRoles().stream().anyMatch(r -> r.getName().matches("WORKER"))
                || userSystem.getRoles().stream().anyMatch(r -> r.getName().matches("CLIENT"))) {
            carRepository.findById(id).ifPresent(
                    car -> carRepository.deleteById(id));
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
                maxYearValue, maxPriceValue, Car.Status.AVAILABLE);
    }

    public Page<Car> getCarsFromSearchButton(String carMark, String maxYear, String maxPrice, Pageable pageable) {
        int maxYearValue = 0;
        BigDecimal maxPriceValue = BigDecimal.ZERO;
        if (!maxYear.isEmpty()) {
            maxYearValue = Integer.parseInt(maxYear);
        }
        if (!maxPrice.isEmpty()) {
            maxPriceValue = BigDecimal.valueOf(Long.parseLong(maxPrice));
        }
        return carRepository.findCarsFromSearchButton(carMark,
                maxYearValue, maxPriceValue, Car.Status.AVAILABLE, pageable);

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
        testDrive.setDateOfTestDrive(date);
        testDrive.setTimeOfTestDrive(time);
        testDrive.setStatus(TestDrive.Status.ACCEPT);
        testDriveRepository.save(testDrive);
    }

    public List<String> findProductionYear() {
        return carRepository.findProductionYear().stream()
                .map(Object::toString).collect(Collectors.toList());
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
        return carRepository.findMark();
    }

    public List<String> findModel() {
        return carRepository.findModel();
    }

}