package com.example.cardealer.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Cars in the application.
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String bodyNumber;

    @Column
    private Integer productionYear;

    @Column
    private String mark;

    @Column
    private String model;

    @Column
    private String ocNumber;

    @Column(unique = true)
    private String regNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Integer distance;

    @Column
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Column
    private Integer capacityEngine;

    @Column
    private Integer powerEngine;

    @Column
    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Column
    private String description;

    @Column
    private Integer testDrive;

    @Column
    private BigDecimal price;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Set<Agreement> agreements;

    @JoinColumn(name = "owner_id")
    private Integer ownerId;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cars_customers", joinColumns = {
            @JoinColumn(name = "car_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")})
    private Set<Customer> customers;


    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Set<Event> events;

    public Set<Customer> getCustomers() {
        if (customers == null) {
            customers = new HashSet<>();
        }
        return customers;
    }

    public void addCustomer(Customer customer) {
        getCustomers().add(customer);
    }


    public enum Transmission {
        MANUAL("Manualna"),
        AUTOMATIC("Automatyczna"),
        DSG("DSG");

        private String type;

        Transmission(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum FuelType {
        DIESEL("Diesel"),
        PETROL("Benzyna"),
        GAS_PETROL("Gaz + Benzyna"),
        ELECTRIC("Elektryczny"),
        HYBRID("Hybryda");

        private String type;

        FuelType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum Status {
        WAIT("Oczekujący"),
        AVAILABLE("Dostępny"),
        SOLD("Sprzedany"),
        BOUGHT("Kupiony"),
        REJECTED("Odrzucony");

        private String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum BodyType {
        HATCHBACK("Hatchback"),
        COMBI("Combi"),
        SEDAN("Sedan"),
        SUV("Suv"),
        VAN("Van"),
        COUPE("Coupe");

        private String type;

        BodyType(String type) {
            this.type = type;
        }

        public String getBodyType() {
            return type;
        }
    }


    public void addAgreement(Agreement agreement) {
        if (agreements == null) {
            agreements = new HashSet<>();
        }
        agreements.add(agreement);
    }

    public void addEvent(Event event) {
        if (events == null) {
            events = new HashSet<>();
        }
        events.add(event);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", bodyNumber='" + bodyNumber + '\'' +
                ", productionYear=" + productionYear +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", ocNumber='" + ocNumber + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", fuelType=" + fuelType +
                ", status=" + status +
                ", distance=" + distance +
                ", bodyType=" + bodyType +
                ", capacityEngine=" + capacityEngine +
                ", powerEngine=" + powerEngine +
                ", transmission=" + transmission +
                ", description='" + description + '\'' +
                ", testDrive=" + testDrive +
                ", price=" + price +
                '}';
    }
}
