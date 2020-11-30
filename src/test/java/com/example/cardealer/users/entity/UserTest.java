package com.example.cardealer.users.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void testAddRole() {
        //given
        User user = new User();
        Role role = new Role();

        //when
        user.addRole(role);

        //then
        assertTrue(user.getRoles().contains(role));
    }
}