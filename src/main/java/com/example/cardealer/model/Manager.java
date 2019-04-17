package com.example.cardealer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * KIEROWNIK
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "managers")
public class Manager extends Worker {


    public Manager() {
    }

}
