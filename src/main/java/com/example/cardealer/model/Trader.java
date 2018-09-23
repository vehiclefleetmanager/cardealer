package com.example.cardealer.model;

import javax.persistence.*;

/**
 * HANDLOWIEC
 */

@Entity
@Table(name = "traders")
public class Trader extends Worker {

    private Integer traderNumber;

    public Trader() {
    }

    public Integer getTraderNumber() {
        return traderNumber;
    }

    public void setTraderNumber(Integer traderNumber) {
        this.traderNumber = traderNumber;
    }
}
