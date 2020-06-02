package com.example.cardealer;

import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.config.Clock;
import com.example.cardealer.customers.boundary.CustomerRepository;
import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.employees.boundary.EmployeeRepository;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.users.boundary.RoleRepository;
import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
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
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final Clock clock;


    @EventListener(ApplicationReadyEvent.class)
    public void initializeApp() {
        log.info("Initializing app on dev profile");
        insertBasicRoles();
        insertAppUsers();
        insertAppCustomers();
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
        Employee adminEmployee = new Employee("Administrator", "Systemu", "Adresowy",
                "500400300", "admin@gmail.com", clock.date(), "Em_n_01");
        Employee newAdminEmployee = employeeRepository.save(adminEmployee);
        User mainUser = new User(newAdminEmployee, true, encoder.encode("az"));
        mainUser.addRole(setUserRole("ADMIN"));

        Employee bossEmployee = new Employee("Boss", "Bossman", "Adresowo",
                "400300200", "boss@gmail.com", clock.date(), "Em_n_02");
        Employee newBossEmployee = employeeRepository.save(bossEmployee);
        User bossUser = new User(newBossEmployee, true, encoder.encode("az"));
        bossUser.addRole(setUserRole("BOSS"));

        Employee workerEmployee = new Employee("Worker", "Workman", "Adresowny",
                "300200100", "worker@gmail.com", clock.date(), "Em_n_03");
        Employee newWorkerEmployee = employeeRepository.save(workerEmployee);
        User workerUser = new User(newWorkerEmployee, true, encoder.encode("az"));
        workerUser.addRole(setUserRole("WORKER"));

        Employee mechanicEmployee = new Employee("Mechanic", "Mechanicman", "Adresowny",
                "200100000", "mechanic@gmail.com", clock.date(), "Em_n_04");
        Employee newMechanicEmployee = employeeRepository.save(mechanicEmployee);
        User mechanicUser = new User(newMechanicEmployee, true, encoder.encode("az"));
        mechanicUser.addRole(setUserRole("MECHANIC"));

        userRepository.saveAll(Arrays.asList(mainUser, bossUser, mechanicUser, workerUser));
    }

    private void insertAppCustomers() {
        Customer golfOwner = new Customer("Jan", "Kowalski", "San Escobar", "1232334",
                "1112223344", "79082113234", "ABC123456", "jako@gmail.com", Customer.Status.PRESENT);
        Car golf = new Car("GOLF1234XYZ", 2012, "VW", "Golf",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis VW golfa", new BigDecimal("26900"));

        Customer fabiaOwner = new Customer("Anna", "Kowalska", "Bytom", "3454556",
                "345667788", "90020413234", "DCE789012", "anko@gmail.com", Customer.Status.PRESENT);
        Car fabia = new Car("FABIA1234XYZ", 2009, "Skoda", "Fabia",
                "OC2017/10/2018", Car.FuelType.DIESEL, 105000, Car.BodyType.HATCHBACK,
                1400, 115, Car.Transmission.MANUAL, "Opis Skody Fabii", new BigDecimal("37890"));

        Customer focusOwner = new Customer("Eryk", "Błaszczak", "San Marino", "1233445",
                "1112223111", "79082113211", "ABC123456", "erbl@gmail.com", Customer.Status.PRESENT);
        Car focus = new Car("FOCUS1234XYZ", 2007, "Ford", "Focus",
                "OC2017/10/2018", Car.FuelType.DIESEL, 234000, Car.BodyType.SEDAN,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis forda focusa", new BigDecimal("15879"));

        Customer yarisOwner = new Customer("Piotr", "Niemiec", "San Escobar", "1232334",
                "1112223222", "79082113222", "ABC123456", "pini@gmail.com", Customer.Status.PRESENT);
        Car yaris = new Car("YARIS1234XYZ", 2013, "Toyota", "Yaris",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis toyouty Yaris", new BigDecimal("68050"));

        Customer i30Owner = new Customer("Zenon", "Koryński", "San Escobar", "1232334",
                "1112223333", "79082113233", "ABC123456", "zeko@gmail.com", Customer.Status.PRESENT);
        Car i30 = new Car("I301234XYZ", 2019, "Hyundai", "I30",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis hyundaia i30", new BigDecimal("45987"));

        Customer mazda3Owner = new Customer("Celina", "Sienkiewicz", "San Escobar", "1232334",
                "1112223444", "79082113244", "ABC123456", "cesi@gmail.com", Customer.Status.PRESENT);
        Car mazda3 = new Car("M31234XYZ", 2015, "Mazda", "3",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis mazdy trzy", new BigDecimal("32870"));

        Customer puntoOwner = new Customer("Łukasz", "Niewiadomski", "San Escobar", "1232334",
                "1112223555", "79082113255", "ABC123456", "luni@gmail.com", Customer.Status.PRESENT);
        Car punto = new Car("PUNTO1234XYZ", 2018, "Fiat", "Punto",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis fiata punto", new BigDecimal("55900"));

        Customer jukeOwner = new Customer("Magdalena", "Iwańska", "San Escobar", "1232334",
                "1112223666", "79082113266", "ABC123456", " maiw@gmail.com", Customer.Status.PRESENT);
        Car juke = new Car("JUKE1234XYZ", 2012, "Nissan", "Juke",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis nissana juke", new BigDecimal("21900"));

        Customer lancerOwner = new Customer("Elżbieta", "Badocha", "San Escobar", "1232334",
                "1112223777", "79082113277", "ABC123456", "elba@gmail.com", Customer.Status.PRESENT);
        Car lancer = new Car("LANCER1234XYZ", 2010, "Mitsubishi", "Lancer",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis mitsubishi lancera", new BigDecimal("19540"));

        Customer a4Owner = new Customer("Damian", "Królikowski", "San Escobar", "1232334",
                "1112223888", "79082113288", "ABC123456", "dakr@gmail.com", Customer.Status.PRESENT);
        Car a4 = new Car("A41234XYZ", 2009, "Audi", "A4",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis audi a4", new BigDecimal("44234"));

        Customer leonOwner = new Customer("Rafał", "Lisiecki", "San Escobar", "1232334",
                "1112223999", "79082113299", "ABC123456", "rali@gmail.com", Customer.Status.PRESENT);
        Car leon = new Car("LEON1234XYZ", 2018, "Seat", "Leon",
                "OC2017/10/2018", Car.FuelType.PETROL, 145000, Car.BodyType.COMBI,
                1800, 115, Car.Transmission.AUTOMATIC, "Opis seata leona", new BigDecimal("74600"));


        Customer newOwnerCar = new Customer("Adam", "Świerzawski", "Zakopane, Krupówki 6",
                "7890123", "987655443", "72072310298", "FGH345678", "adsw@gmail.com", Customer.Status.FUTURE);


        golfOwner.addCar(golf);
        fabiaOwner.addCar(fabia);
        focusOwner.addCar(focus);
        yarisOwner.addCar(yaris);
        i30Owner.addCar(i30);
        mazda3Owner.addCar(mazda3);
        puntoOwner.addCar(punto);
        jukeOwner.addCar(juke);
        lancerOwner.addCar(lancer);
        a4Owner.addCar(a4);
        leonOwner.addCar(leon);

        User clientOwnerGolf = new User(golfOwner, true, encoder.encode("az"));
        clientOwnerGolf.addRole(setUserRole("CLIENT"));

        User clientOwnerFabia = new User(fabiaOwner, true, encoder.encode("az"));
        clientOwnerFabia.addRole(setUserRole("CLIENT"));

        User clientOwnerFocus = new User(focusOwner, true, encoder.encode("az"));
        clientOwnerFocus.addRole(setUserRole("CLIENT"));

        User clientOwnerYaris = new User(yarisOwner, true, encoder.encode("az"));
        clientOwnerYaris.addRole(setUserRole("CLIENT"));

        User clientOwnerI30 = new User(i30Owner, true, encoder.encode("az"));
        clientOwnerI30.addRole(setUserRole("CLIENT"));

        User clientOwnerMazda = new User(mazda3Owner, true, encoder.encode("az"));
        clientOwnerMazda.addRole(setUserRole("CLIENT"));

        User clientOwnerPunto = new User(puntoOwner, true, encoder.encode("az"));
        clientOwnerPunto.addRole(setUserRole("CLIENT"));

        User clientOwnerJuke = new User(jukeOwner, true, encoder.encode("az"));
        clientOwnerJuke.addRole(setUserRole("CLIENT"));

        User clientOwnerLancer = new User(lancerOwner, true, encoder.encode("az"));
        clientOwnerLancer.addRole(setUserRole("CLIENT"));

        User clientOwnerA4 = new User(a4Owner, true, encoder.encode("az"));
        clientOwnerA4.addRole(setUserRole("CLIENT"));

        User clientOwnerLeon = new User(leonOwner, true, encoder.encode("az"));
        clientOwnerLeon.addRole(setUserRole("CLIENT"));

        User clientNewOwnerCar = new User(newOwnerCar, true, encoder.encode("az"));
        clientNewOwnerCar.addRole(setUserRole("CLIENT"));

        userRepository.saveAll(Arrays.asList(clientOwnerGolf, clientOwnerFabia, clientOwnerFocus, clientOwnerYaris,
                clientOwnerI30, clientOwnerPunto, clientOwnerJuke, clientOwnerLancer, clientOwnerA4, clientOwnerLeon));

        customerRepository.saveAll(Arrays.asList(golfOwner, fabiaOwner, focusOwner, yarisOwner,
                i30Owner, mazda3Owner, puntoOwner, jukeOwner, lancerOwner, a4Owner, leonOwner, newOwnerCar));
    }

    private Role setUserRole(String role) {
        return roleRepository.findByName(role);
    }
}
