package com.example.cardealer.repairs.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepairTest {

    @Test
    public void testAddPart() {
        //given
        Repair repair = new Repair();
        Part part = new Part();

        //when
        repair.addPart(part);

        //then
        assertTrue(repair.getParts().contains(part));
    }
}