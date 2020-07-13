package com.example.cardealer.customers.entity;

import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.users.entity.Person;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

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
public class Customer extends Person {

    @Column(unique = true)
    private String tin;

    @Column(unique = true)
    private String pesel;

    private String idNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Collection<Car> cars = new HashSet<>();

    public Customer(String tin, String pesel,
                    String idNumber) {
        this.tin = tin;
        this.pesel = pesel;
        this.idNumber = idNumber;
    }

    public Customer(String firstName, String lastName, String address,
                    String phoneNumber, String tin, String pesel,
                    String idNumber, String email, Status status) {
        super();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
        this.setActive(true);
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

    public Collection<Car> getCars() {
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
