package com.example.cardealer.users.entity;

import com.example.cardealer.entities.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
