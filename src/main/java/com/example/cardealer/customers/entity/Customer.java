package com.example.cardealer.customers.entity;


import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Customers in the application.
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends User {

    @Column(unique = true)
    private String tin;

    @Column(unique = true)
    private String pesel;

    private String address;

    private String idNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "carOwner", cascade = CascadeType.ALL)
    //@JoinColumn(name = "customer_id")
    private Set<Car> cars = new HashSet<>();

    public Customer(String tin, String pesel,
                    String idNumber) {
        this.tin = tin;
        this.pesel = pesel;
        this.idNumber = idNumber;
    }

    public Customer(String firstName, String lastName, String address,
                    String phoneNumber, String tin, String pesel,
                    String idNumber, String email, String password, Role role, Status status) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setPassword(password);
        addRole(role);
        this.tin = tin;
        this.pesel = pesel;
        this.idNumber = idNumber;
        this.status = status;
    }

    /* This constructor is use with create cession events*/
    public Customer(String firstName, String lastName, String address, String phoneNumber, String tin, String pesel, String idNumber, String email, Status status) {
        super();
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        this.tin = tin;
        this.pesel = pesel;
        this.idNumber = idNumber;
        this.status = status;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public Set<Car> getCars() {
        return cars;
    }


    public enum Status {
        ABSENT("Były właściciel"),
        PRESENT("Obecny właściciel"),
        FUTURE("Przyszły właściciel");

        public String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
