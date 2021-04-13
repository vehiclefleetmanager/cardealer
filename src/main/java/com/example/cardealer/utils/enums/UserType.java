package com.example.cardealer.utils.enums;

public enum UserType {
    CLIENT("Klient"),
    WORKER("Pracownik");

    private String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }
}
