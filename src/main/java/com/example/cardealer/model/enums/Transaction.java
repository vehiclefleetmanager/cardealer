package com.example.cardealer.model.enums;

public enum Transaction {
    PURCHASE("zakupionych"),
    RENOUNCEMENT("odstąpionych"),
    SALE("sprzedanych"),
    WAITING("oczekujących"),
    TESTING("testowanie");

    private String type;

    Transaction(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
