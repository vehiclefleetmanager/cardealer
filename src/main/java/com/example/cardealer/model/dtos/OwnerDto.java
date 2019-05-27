package com.example.cardealer.model.dtos;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OwnerDto {
    private Integer ownerId;
    private String lastName;
    private String firstName;
    private String address;
    private String tin;
    private String pesel;
    private String phoneNumber;
    private String email;
    private List<Car> cars;
    private Owner.Status status;
}
