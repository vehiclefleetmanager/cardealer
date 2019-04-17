package com.example.cardealer.service;

import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public Event getEvent(Integer id) {
        return eventRepository.findById(id).get();
    }

    public Customer findCustomerByCarId(Integer id) {
        return eventRepository.findCustomerByCarId(id);
    }

    public Event findEventByCarId(Integer id) {
        return eventRepository.findEventByCarId(id);
    }

    public List<Event> findEventByTesting(Transaction transaction) {
        return eventRepository.findEventByTransactionIsLikeTesting(transaction);
    }
}
