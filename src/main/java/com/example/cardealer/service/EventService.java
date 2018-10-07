package com.example.cardealer.service;

import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Event;
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
        Customer customerByCarId = eventRepository.findCustomerByCarId(id);
        return customerByCarId;
    }

    public Event findEventByCarId(Integer id) {
        Event eventByCarId = eventRepository.findEventByCarId(id);
        return eventByCarId;
    }
}
