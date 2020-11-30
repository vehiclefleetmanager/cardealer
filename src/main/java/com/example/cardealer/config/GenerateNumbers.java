package com.example.cardealer.config;

import com.example.cardealer.events.entity.Event;

import java.util.Collection;

public interface GenerateNumbers {
    String generateInvoicesNumbers(Collection collection, Event event);

    String generateAgreementsNumbers(Collection collection, Event event);
}
