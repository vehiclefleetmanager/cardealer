package com.example.cardealer.config;

import org.springframework.stereotype.Component;

@Component
public class SystemTemporaryPassword implements TemporaryPassword {
    @Override
    public String temporaryPassword() {
        char[] str = new char[20];
        for (int i = 0; i < 5; i++) {
            str[i] = (char) (((int) (Math.random() * 26)) + (int) 'a');
        }
        return (new String(str, 0, 5));
    }
}
