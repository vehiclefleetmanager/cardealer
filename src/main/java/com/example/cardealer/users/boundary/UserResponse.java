package com.example.cardealer.users.boundary;

import com.example.cardealer.users.entity.Role;
import com.example.cardealer.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    boolean isActive;
    Collection<String> roles;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isActive(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList())
        );
    }
}
