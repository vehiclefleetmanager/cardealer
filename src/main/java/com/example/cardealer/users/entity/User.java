package com.example.cardealer.users.entity;

import com.example.cardealer.entities.BaseEntity;
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
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private boolean active = true;

    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();


    public void addRole(Role role) {
        if (roles.isEmpty() && getRoles().stream().noneMatch(r -> r.getName().matches(r.getName()))) {
            roles.add(role);
        }
    }

    public void removeRole(Role role) {
        if (getRoles().stream().noneMatch(r -> r.getName().matches(r.getName()))) {
            getRoles().remove(role);
        }
    }
}
