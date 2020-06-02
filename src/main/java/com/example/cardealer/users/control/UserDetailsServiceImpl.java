package com.example.cardealer.users.control;

import com.example.cardealer.users.boundary.UserRepository;
import com.example.cardealer.users.entity.AuthorizeUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(AuthorizeUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Nie ma takiego użytkownika"));
    }
}




























