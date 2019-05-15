package com.example.cardealer.repository;

import com.example.cardealer.model.Owner;
import com.example.cardealer.model.dtos.OwnerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
   /* @Query("select o.firstName, o.lastName from Owner o join Car c on o.ownerId=c.ownerId where c.id = ?1")
    OwnerDto findOwnerNameByCarOwnerId(Integer carId);*/
}
