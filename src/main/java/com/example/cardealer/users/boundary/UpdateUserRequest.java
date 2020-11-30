package com.example.cardealer.users.boundary;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
