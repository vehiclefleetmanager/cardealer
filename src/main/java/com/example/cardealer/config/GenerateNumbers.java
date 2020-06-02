package com.example.cardealer.config;

import com.example.cardealer.utils.enums.Transaction;

import java.util.Collection;

public interface GenerateNumbers {
    String generateInvoicesNumbers(Collection collection, Transaction transaction);

    String generateAgreementsNumbers(Collection collection, Transaction transaction);
}
