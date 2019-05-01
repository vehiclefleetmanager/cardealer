package com.example.cardealer.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private String address;
    private String tin;
    private String pesel;
    private Integer phoneNumber;
    private Date testingDate;
    private String email;
}
