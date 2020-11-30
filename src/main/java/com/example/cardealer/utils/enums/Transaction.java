package com.example.cardealer.utils.enums;

public enum Transaction {
    PURCHASE("ZAKUP"),
    CESSION("ODSTĄPEINIE"),
    SALE("SPRZEDAŻ"),
    EMPTY("PUSTA"),
    REPAIR("NAPRAWA");

    private String typeName;

    Transaction(String type) {
        this.typeName = type;
    }

    public String getType() {
        return typeName;
    }
}
