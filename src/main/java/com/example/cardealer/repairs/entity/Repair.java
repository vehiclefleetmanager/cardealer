package com.example.cardealer.repairs.entity;


import com.example.cardealer.cars.entity.Car;
import com.example.cardealer.documents.entiity.Invoice;
import com.example.cardealer.employees.entity.Employee;
import com.example.cardealer.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "repairs")
public class Repair extends BaseEntity {
    @OneToMany
    Collection<Part> parts = new HashSet<>();
    private LocalDate repairDate;
    private BigDecimal repairAmount;
    private String repairDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @OneToOne
    @JoinColumn(name = "repair_invoice_id")
    private Invoice repairInvoice;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public void addPart(Part part) {
        parts.add(part);
    }
}
