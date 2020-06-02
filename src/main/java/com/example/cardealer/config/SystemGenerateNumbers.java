package com.example.cardealer.config;

import com.example.cardealer.utils.enums.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
public class SystemGenerateNumbers implements GenerateNumbers {
    private final Clock clock;

    @Override
    public String generateInvoicesNumbers(Collection collection, Transaction transaction) {
        int invNumber;
        int month = clock.date().getMonth().getValue();
        int year = clock.date().getYear();
        String sufix = "";
        String prefix = "FV/";
        if (transaction.name().matches(Transaction.REPAIR.name())) {
            sufix = "N";
        } else if (transaction.name().matches(Transaction.PURCHASE.name())) {
            sufix = "Z";
        } else if (transaction.name().matches(Transaction.CESSION.name())) {
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
    public String generateAgreementsNumbers(Collection collection, Transaction transaction) {
        int agrNumber;
        int month = clock.date().getMonth().getValue();
        int year = clock.date().getYear();
        String sufix = "";
        String prefix = "U/";
        if (transaction.name().matches(Transaction.REPAIR.name())) {
            sufix = "N";
        } else if (transaction.name().matches(Transaction.PURCHASE.name())) {
            sufix = "Z";
        } else if (transaction.name().matches(Transaction.CESSION.name())) {
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
