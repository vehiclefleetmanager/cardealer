package com.example.cardealer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ownerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "tin", unique = true)
    private Long tin;

    @Column(name = "pesel", unique = true)
    private Long pesel;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "e_mail", unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private Set<Car> cars;

    public enum Status {
        ABSENT("Były właściciel"),
        PRESENT("Obecny właściciel");

        public String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public Set<Car> getCars() {
        if (cars == null) {
            cars = new HashSet<>();
        }
        return cars;
    }

    public void addCar(Car car) {
        car.setOwnerId(this.getOwnerId());
        getCars().add(car);
    }
}
