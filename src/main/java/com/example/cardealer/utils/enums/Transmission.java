package com.example.cardealer.utils.enums;

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
