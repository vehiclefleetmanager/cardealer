package com.example.cardealer.model.dtos;

import com.example.cardealer.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OwnerDto {
    private Integer ownerId;
    private String lastName;
    private String firstName;
    private String address;
    private Long tin;
    private Long pesel;
    private String phoneNumber;
    private String email;
    private Owner.Status status;
}
