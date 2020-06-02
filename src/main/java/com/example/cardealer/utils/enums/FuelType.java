package com.example.cardealer.utils.enums;

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
