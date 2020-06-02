package com.example.cardealer.users.boundary;

import lombok.Data;

@Data
public class UpdateUserPasswordRequest {
    private Long id;
    private String email;
    private String password;
    private String confPass;
}
