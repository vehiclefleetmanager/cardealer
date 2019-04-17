package com.example.cardealer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class describes the Traders in the application.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "traders")
public class Trader extends Worker {

    private static Integer traderNumber = 0;

    public Trader() {
        traderNumber++;
    }
}
