package com.example.cardealer.events.entity;

import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.entities.BaseEntity;
import com.example.cardealer.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tests_drives")
public class TestDrive extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dateOfTestDrive;

    private LocalTime timeOfTestDrive;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NEW("Nowa"),
        ACCEPT("Oczekująca"),
        END("Zakończona");
        private String typeName;

        Status(String type) {
            this.typeName = type;
        }

        public String getTypeName() {
            return typeName;
        }

    }

}
