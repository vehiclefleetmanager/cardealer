package com.example.cardealer.repository;

import com.example.cardealer.model.Owner;
import com.example.cardealer.model.dtos.OwnerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    @Query("select o from Owner o where o.ownerId = ?1")
    Optional<Owner> getOwnerById(Integer ownerId);
}
