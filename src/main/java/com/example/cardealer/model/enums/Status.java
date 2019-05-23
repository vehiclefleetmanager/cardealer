package com.example.cardealer.model.enums;

public enum Status {
    WAIT("Oczekujący"),
    AVAILABLE("Dostępny"),
    SOLD("Sprzedany"),
    BOUGHT("Kupiony"),
    REJECTED("Odrzucony");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}