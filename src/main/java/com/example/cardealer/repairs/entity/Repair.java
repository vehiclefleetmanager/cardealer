package com.example.cardealer.repairs.entity;


import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "repairs")
public class Repair extends BaseEntity {

    private LocalDate repairDate;
    private BigDecimal repairAmount;
    private String repairDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "repair_id")
    Set<Part> parts = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    public Repair(String description) {
        super();
        this.repairDescription = description;
    }

    public void addPart(Part part) {
        parts.add(part);
    }

    public Set<Part> getParts() {
        return parts;
    }
}
