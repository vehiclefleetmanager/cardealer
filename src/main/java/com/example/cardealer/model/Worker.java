package com.example.cardealer.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * This class describes the Workers in the application.
 */

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "workers")
//@Inheritance(strategy = InheritanceType.JOINED)
public class Worker extends Person {

    @Column(name = "date_of_employment")
    private LocalDate employmentDate;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private Set<Event> events;

    public Set<Event> getEvents() {
        if (events == null) {
            events = new HashSet<>();
        }
        return events;
    }

    public void addEvent(Event event) {
        getEvents().add(event);
    }
}
