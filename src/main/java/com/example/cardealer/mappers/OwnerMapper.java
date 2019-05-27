package com.example.cardealer.mappers;

import com.example.cardealer.model.Owner;
import com.example.cardealer.model.dtos.OwnerDto;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper implements Mapper<Owner, OwnerDto> {
    @Override
    public OwnerDto map(Owner from) {
        return OwnerDto.builder()
                .ownerId(from.getOwnerId())
                .address(from.getAddress())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .phoneNumber(from.getPhoneNumber())
                .pesel(String.valueOf(from.getPesel()))
                .tin(String.valueOf(from.getTin()))
                .email(from.getEmail())
                .status(from.getStatus())
                .build();
    }

    @Override
    public Owner reverse(OwnerDto to) {
        return Owner.builder()
                .ownerId(to.getOwnerId())
                .firstName(to.getFirstName())
                .address(to.getAddress())
                .phoneNumber(to.getPhoneNumber())
                .lastName(to.getLastName())
                .pesel(Long.valueOf(to.getPesel()))
                .tin(Long.valueOf(to.getTin()))
                .email(to.getEmail())
                .status(to.getStatus())
                .build();
    }
}
