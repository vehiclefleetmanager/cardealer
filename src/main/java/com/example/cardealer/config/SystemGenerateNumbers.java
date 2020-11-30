package com.example.cardealer.config;

import com.example.cardealer.events.entity.Cession;
import com.example.cardealer.events.entity.Event;
import com.example.cardealer.events.entity.Purchase;
import com.example.cardealer.repairs.entity.Repair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
public class SystemGenerateNumbers implements GenerateNumbers {
    private final Clock clock;

    @Override
    public String generateInvoicesNumbers(Collection collection, Event event) {
        int invNumber;
        int month = clock.date().getMonth().getValue();
        int year = clock.date().getYear();
        String sufix = "";
        String prefix = "FV/";
        if (event.getClass().equals(Repair.class)) {
            sufix = "N";
        } else if (event.getClass().equals(Purchase.class)) {
            sufix = "Z";
        } else if (event.getClass().equals(Cession.class)) {
            sufix = "C";
        } else {
            sufix = "S";
        }
        if (collection.size() == 0) {
            invNumber = 1;
        } else {
            invNumber = collection.size() + 1;
        }
        return prefix + invNumber + "/" + month + "/" + year + "/" + sufix;
    }

    @Override
    public String generateAgreementsNumbers(Collection collection, Event event) {
        int agrNumber;
        int month = clock.date().getMonth().getValue();
        int year = clock.date().getYear();
        String sufix = "";
        String prefix = "U/";
        if (event.getClass().equals(Repair.class)) {
            sufix = "N";
        } else if (event.getClass().equals(Purchase.class)) {
            sufix = "Z";
        } else if (event.getClass().equals(Cession.class)) {
            sufix = "C";
        } else {
            sufix = "S";
        }
        if (collection.size() == 0) {
            agrNumber = 1;
        } else {
            agrNumber = collection.size() + 1;
        }
        return prefix + agrNumber + "/" + month + "/" + year + "/" + sufix;
    }
}
