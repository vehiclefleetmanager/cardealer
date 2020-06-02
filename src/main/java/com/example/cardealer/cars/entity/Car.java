package com.example.cardealer.cars.entity;

import com.example.cardealer.customers.entity.Customer;
import com.example.cardealer.entities.BaseEntity;
import com.example.cardealer.events.entity.TestDrive;
import com.example.cardealer.repairs.entity.Repair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Cars in the application.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(unique = true)
    private String bodyNumber;
    private Integer productionYear;
    private String mark;
    private String model;
    private String ocNumber;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer distance;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    private Integer capacityEngine;
    private Integer powerEngine;
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    private String description;
    private Integer testDrive;
    private BigDecimal price = new BigDecimal(0);

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "repairs")
    private Set<Repair> repairs = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tests_drives")
    private Set<TestDrive> testDrives = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer carOwner;

    public Car(String bodyNumber, Integer productionYear, String mark, String model,
               String ocNumber, FuelType fuelType, Integer distance, BodyType bodyType,
               Integer capacityEngine, Integer powerEngine, Transmission transmission,
               String description, BigDecimal price) {
        super();
        this.status = Status.AVAILABLE;
        this.testDrive = 0;
        this.bodyNumber = bodyNumber;
        this.productionYear = productionYear;
        this.mark = mark;
        this.model = model;
        this.ocNumber = ocNumber;
        this.fuelType = fuelType;
        this.distance = distance;
        this.bodyType = bodyType;
        this.capacityEngine = capacityEngine;
        this.powerEngine = powerEngine;
        this.transmission = transmission;
        this.description = description;
        this.price = price;
    }

    public void addRepair(Repair repair) {
        repairs.add(repair);
    }

    public void addTestDrive(TestDrive testDrive) {
        testDrives.add(testDrive);
    }

    public enum Transmission {
        MANUAL("Manualna"),
        AUTOMATIC("Automatyczna"),
        DSG("DSG");
        private String typeName;

        Transmission(String type) {
            this.typeName = type;
        }

        public static Transmission findByName(String trans) {
            Transmission[] values = Transmission.values();
            Transmission transType = null;
            for (Transmission bufTrans : values) {
                if (bufTrans.name().matches(trans)) {
                    transType = bufTrans;
                }
            }
            return transType;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public enum FuelType {
        DIESEL("Diesel"),
        PETROL("Benzyna"),
        GAS_PETROL("Gaz + Benzyna"),
        ELECTRIC("Elektryczny"),
        HYBRID("Hybryda");
        private String typeName;

        FuelType(String type) {
            this.typeName = type;
        }

        public static FuelType findByName(String fuel) {
            FuelType[] values = FuelType.values();
            FuelType fuelType = null;
            for (FuelType bufType : values) {
                if (bufType.name().matches(fuel)) {
                    fuelType = bufType;
                }
            }
            return fuelType;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public enum Status {
        WAIT("Oczekujący"),
        ACCEPTED("Zatwierdzony"),
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
        COMBI("Kombi"),
        SEDAN("Sedan"),
        SUV("Suv"),
        VAN("Van"),
        COUPE("Coupe");
        private String typeName;

        BodyType(String type) {
            this.typeName = type;
        }

        public static BodyType findByName(String types) {
            BodyType[] values = BodyType.values();
            BodyType bodyType = null;
            for (BodyType bufType : values) {
                if (bufType.name().matches(types)) {
                    bodyType = bufType;
                }
            }
            return bodyType;
        }

        public String getTypeName() {
            return typeName;
        }
    }

}
