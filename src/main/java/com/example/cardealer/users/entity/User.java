package com.example.cardealer.users.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Person {


    private String password;

    private boolean active;

    @ManyToMany(cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();


    /*constructor for decorate new Employee*/
    public User(Person person, String pass) {
        super();
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setAddress(person.getAddress());
        setPhoneNumber(person.getPhoneNumber());
        setEmail(person.getEmail());
        this.active = true;
        this.password = pass;
    }

    /*constructor for decorate new Customer*/
    public User(Person person, boolean isActive, String pass) {
        super();
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setAddress(person.getAddress());
        setPhoneNumber(person.getPhoneNumber());
        setEmail(person.getEmail());
        setActive(isActive);
        this.password = pass;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
