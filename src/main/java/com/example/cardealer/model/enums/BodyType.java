package com.example.cardealer.model.enums;

public enum BodyType {
    HATCHBACK("Hatchback"),
    COMBI("Combi"),
    SEDAN("Sedan"),
    SUV("Suv"),
    VAN("Van"),
    COUPE("Coupe");

    private String type;

    BodyType(String type) {
        this.type = type;
    }

    public String getBodyType() {
        return type;
    }
}