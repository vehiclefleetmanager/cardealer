package com.example.cardealer;

import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.config.Clock;
import com.example.cardealer.config.SystemGenerateNumbers;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.documents.boundary.AgreementRepository;
import com.example.cardealer.documents.boundary.InvoiceRepository;
import com.example.cardealer.documents.entiity.Agreement;
import com.example.cardealer.documents.entiity.Invoice;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.events.boundary.CessionRepository;
import com.example.cardealer.events.entity.Cession;
import com.example.cardealer.events.entity.Event;
import com.example.cardealer.events.entity.Purchase;
import com.example.cardealer.events.entity.Sale;
import com.example.cardealer.repairs.boundary.RepairRepository;
import com.example.cardealer.repairs.entity.Part;
import com.example.cardealer.repairs.entity.Repair;
import com.example.cardealer.users.boundary.RoleRepository;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
import com.example.cardealer.utils.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@AllArgsConstructor
@Slf4j
@Profile("dev")
public class StartupData {

    private final UserRepository userRepository;
    private final CessionRepository cessionRepository;
    private final RoleRepository roleRepository;
    private final RepairRepository repairRepository;
    private final AgreementRepository agreementRepository;
    private final InvoiceRepository invoiceRepository;
    private final PasswordEncoder encoder;
    private final SystemGenerateNumbers systemGenerateNumbers;
    private final Clock clock;


    @EventListener(ApplicationReadyEvent.class)
    public void initializeApp() {
        log.info("Initializing app on dev profile");
        insertBasicRoles();
        insertAppUsers();
        insertAppCustomers();
        makeEventCession();
        makeRepairCar();
        makeSaleCar();
    }

    private void insertBasicRoles() {
        Role adminRule = new Role("ADMIN");
        Role bossRule = new Role("BOSS");
        Role mechanicRule = new Role("MECHANIC");
        Role workerRule = new Role("WORKER");
        Role clientRule = new Role("CLIENT");
        roleRepository.saveAll(Arrays.asList(adminRule, bossRule, mechanicRule, workerRule, clientRule));
    }

    private void insertAppUsers() {
        User adminUser = new Employee("Administrator", "Systemu",
                "500400300", "admin@gmail.com", encoder.encode("az"),
                setUserRole("ADMIN"), clock.date(), "EN1");

        User bossUser = new Employee("Boss", "Bossman",
                "400300200", "boss@gmail.com", encoder.encode("az"),
                setUserRole("BOSS"), clock.date(), "EN2");

        userRepository.saveAll(Arrays.asList(adminUser, bossUser));
    }

    private void insertAppCustomers() {
        Customer golfOwner = new Customer("Jan", "Kowalski", "San Escobar", "1232334",
                "1112223344", "79082113234", "ABC123456", "jako@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car golf = new Car("GOLF1234XYZ", 2012, "VW", "Golf",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis VW golfa", new BigDecimal("26900"));

        Customer fabiaOwner = new Customer("Anna", "Kowalska", "Bytom", "3454556",
                "345667788", "90020413234", "DCE789012", "anko@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car fabia = new Car("FABIA1234XYZ", 2009, "Skoda", "Fabia",
                "OC2017/10/2018", Car.FuelType.DIESEL, 105000, Car.BodyType.HATCHBACK,
                1400, 115, Car.Transmission.MANUAL, "Opis Skody Fabii", new BigDecimal("37890"));

        Customer yarisOwner = new Customer("Piotr", "Niemiec", "San Escobar", "1232334",
                "1112223222", "79082113222", "ABC123456", "pini@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car yaris = new Car("YARIS1234XYZ", 2013, "Toyota", "Yaris",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis toyouty Yaris", new BigDecimal("68050"));

        Customer i30Owner = new Customer("Zenon", "Koryński", "San Escobar", "1232334",
                "1112223333", "79082113233", "ABC123456", "zeko@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car i30 = new Car("I301234XYZ", 2019, "Hyundai", "I30",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis hyundaia i30", new BigDecimal("45987"));

        Customer mazda3Owner = new Customer("Celina", "Sienkiewicz", "San Escobar", "1232334",
                "1112223444", "79082113244", "ABC123456", "cesi@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car mazda3 = new Car("M31234XYZ", 2015, "Mazda", "3",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis mazdy trzy", new BigDecimal("32870"));

        Customer puntoOwner = new Customer("Łukasz", "Niewiadomski", "San Escobar", "1232334",
                "1112223555", "79082113255", "ABC123456", "luni@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car punto = new Car("PUNTO1234XYZ", 2018, "Fiat", "Punto",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis fiata punto", new BigDecimal("55900"));

        Customer jukeOwner = new Customer("Magdalena", "Iwańska", "San Escobar", "1232334",
                "1112223666", "79082113266", "ABC123456", "maiw@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car juke = new Car("JUKE1234XYZ", 2012, "Nissan", "Juke",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis nissana juke", new BigDecimal("21900"));

        Customer lancerOwner = new Customer("Elżbieta", "Badocha", "San Escobar", "1232334",
                "1112223777", "79082113277", "ABC123456", "elba@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car lancer = new Car("LANCER1234XYZ", 2010, "Mitsubishi", "Lancer",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis mitsubishi lancera", new BigDecimal("19540"));

        Customer a4Owner = new Customer("Damian", "Królikowski", "San Escobar", "1232334",
                "1112223888", "79082113288", "ABC123456", "dakr@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car a4 = new Car("A41234XYZ", 2009, "Audi", "A4",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis audi a4", new BigDecimal("44234"));

        golfOwner.addCar(golf);
        fabiaOwner.addCar(fabia);
        yarisOwner.addCar(yaris);
        i30Owner.addCar(i30);
        mazda3Owner.addCar(mazda3);
        puntoOwner.addCar(punto);
        jukeOwner.addCar(juke);
        lancerOwner.addCar(lancer);
        a4Owner.addCar(a4);

        userRepository.saveAll(Arrays.asList(golfOwner, fabiaOwner, yarisOwner,
                i30Owner, mazda3Owner, puntoOwner, jukeOwner, lancerOwner, a4Owner));
    }

    private Role setUserRole(String role) {
        return roleRepository.findByName(role);
    }

    private Cession prepareEventCession() {
        Customer customer = new Customer("Jan", "Janowski", "San Escobar", "1232334",
                "545879612", "79083113299", "JJ123456", "jaja@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car car = new Car("DOKKER1234XYZ", 2018, "Dacia", "Dokker",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.VAN,
                1350, 66, Car.Transmission.MANUAL, "Opis dacii dokker",
                new BigDecimal("28600"), Car.Status.WAIT);
        car.setCarOwner(customer);
        customer.addCar(car);
        return new Cession(car, customer, clock.date());
    }

    private void makeEventCession() {
        Cession event = prepareEventCession();
        String content = event.getCar().getMark() + " " + event.getCar().getModel() + " " + event.getCar().getBodyNumber();
        Agreement agreement = new Agreement(event.getCustomer(), event, clock.date(), content);
        String agrNumb = systemGenerateNumbers.generateAgreementsNumbers(agreementRepository.findAll(), event);
        agreement.setAgreementNumber(agrNumb);
        agreement.setTransaction(Transaction.CESSION);
        agreement.setEvent(event);
        event.setAgreement(agreement);
        agreementRepository.save(agreement);
    }

    private void makeRepairCar() {
        Employee employee = new Employee("Mechanic", "Mechanicman",
                "200100000", "mechanic@gmail.com", encoder.encode("az"),
                setUserRole("MECHANIC"), clock.date(), "EN4");
        Customer customer = new Customer("Rafał", "Lisiecki", "San Escobar", "1232334",
                "1112223999", "79082113299", "ABC123456", "rali@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car car = new Car("LEON1234XYZ", 2018, "Seat", "Leon",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis seata leona", new BigDecimal("74600"));
        customer.addCar(car);
        car.setCarOwner(customer);
        car.setStatus(Car.Status.AVAILABLE);
        Repair repair = new Repair();
        repair.setCar(car);
        repair.setRepairAmount(new BigDecimal("1140"));
        repair.setRepairDate(clock.date());
        repair.setRepairDescription("Naprawa zawieszania przedniego");
        repair.addPart(new Part("amortyzator lewy", new BigDecimal("450"), car.getBodyNumber()));
        repair.addPart(new Part("amortyzator prawy", new BigDecimal("450"), car.getBodyNumber()));
        repair.addPart(new Part("łącznik prawy", new BigDecimal("120"), car.getBodyNumber()));
        repair.addPart(new Part("łącznik lewy", new BigDecimal("120"), car.getBodyNumber()));
        repair.setEmployee(employee);
        repairRepository.save(repair);
    }

    private void prepareDataForEventSale() {
        Invoice purchase = prepareDataForEventPurchase();
        Car car = purchase.getAgreement().getEvent().getCar();
        Customer customer = new Customer("Adam", "Świerzawski", "Zakopane, Krupówki 6",
                "7890123", "987655443", "72072310298", "FGH345678", "adsw@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.FUTURE);
        customer.addCar(car);
        car.setCarOwner(customer);
        car.setStatus(Car.Status.SOLD);
        Sale sale = makeSaleEvent(customer, car, car.getPrice());

        Agreement agreement = makeAgreement(customer, car, sale);
        agreement.setAgreementAmount(car.getPrice());
        sale.setAgreement(agreement);

        Invoice invoice = makeInvoice(sale, agreement);

        invoiceRepository.save(invoice);
        invoice.setEmployee(purchase.getEmployee());
        invoiceRepository.save(invoice);
    }

    private Invoice prepareDataForEventPurchase() {
        Customer customer = new Customer("Eryk", "Błaszczak", "San Marino", "1233445",
                "1112223111", "79082113211", "ABC123456", "erbl@gmail.com", encoder.encode("az"),
                setUserRole("CLIENT"), Customer.Status.PRESENT);
        Car car = new Car("FOCUS1234XYZ", 2007, "Ford", "Focus",
                "OC2017/10/2018", Car.FuelType.DIESEL, 234000, Car.BodyType.SEDAN,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis forda focusa", new BigDecimal("15879"));
        Employee employee = new Employee("Worker", "Workman", "300200100",
                "worker@gmail.com", encoder.encode("az"),
                setUserRole("WORKER"), clock.date(), "EN3");
        car.setStatus(Car.Status.BOUGHT);
        customer.removeCar(car);
        BigDecimal endAmountOfCar = prepareAmountOfCarForPurchaseEvent(car);

        Event purchase = makePurchaseEvent(customer, car, endAmountOfCar);

        Agreement agreement = makeAgreement(customer, car, purchase);
        agreement.setAgreementAmount(endAmountOfCar);
        purchase.setAgreement(agreement);

        Invoice invoice = makeInvoice(purchase, agreement);
        invoice.setEmployee(employee);

        return invoiceRepository.save(invoice);
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

    private Sale makeSaleEvent(Customer customer, Car car, BigDecimal price) {
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setCar(car);
        sale.setEventDate(clock.date());
        sale.setSaleAmount(price);
        return sale;
    }

    private Purchase makePurchaseEvent(Customer customer, Car car, BigDecimal price) {
        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setCar(car);
        purchase.setEventDate(clock.date());
        purchase.setPurchaseAmount(price);
        return purchase;
    }

    private Agreement makeAgreement(Customer customer, Car car, Event event) {
        Agreement agreement = new Agreement();
        agreement.setEvent(event);
        agreement.setCustomer(customer);
        agreement.setAgreementNumber(systemGenerateNumbers
                .generateAgreementsNumbers(agreementRepository.findAll(), event));
        agreement.setContent(
                car.getMark() + " " + car.getModel() + " " +
                        car.getBodyNumber());
        agreement.setTransaction(whichTransaction(event));
        agreement.setCreatedAt(clock.date());
        agreement.setEvent(event);
        return agreement;
    }

    private Invoice makeInvoice(Event event, Agreement agreement) {
        Invoice invoice = new Invoice();
        invoice.setAgreement(event.getAgreement());
        invoice.setCustomer(event.getCustomer());
        invoice.setCreatedAt(clock.date());
        invoice.setInvoiceAmount(agreement.getAgreementAmount());
        invoice.setTransaction(whichTransaction(event));
        invoice.setInvoiceNumber(systemGenerateNumbers.generateInvoicesNumbers(
                invoiceRepository.findAll(), event));
        event.getAgreement().setInvoice(invoice);
        return invoice;
    }

    private Transaction whichTransaction(Event event) {
        Transaction transaction = Transaction.EMPTY;
        if (event.getClass().equals(Purchase.class)) {
            transaction = Transaction.PURCHASE;
        } else if (event.getClass().equals(Sale.class)) {
            transaction = Transaction.SALE;
        }
        return transaction;
    }


    private void makeSaleCar() {
        prepareDataForEventSale();
    }
}
