package com.example.cardealer.mappers;

public interface Mapper<F,T> {
    T map(F from);
    F reverse(T to);
}
