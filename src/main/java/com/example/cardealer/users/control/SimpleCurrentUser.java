package com.example.cardealer.users.control;

import com.example.cardealer.users.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SimpleCurrentUser implements CurrentUser {

    @Override
    public User getUser() {
        return ((AuthorizeUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getUser();
    }
}
