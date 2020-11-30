package com.example.cardealer.cars.entity;

import com.example.cardealer.events.entity.TestDrive;
import com.example.cardealer.repairs.entity.Repair;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarTest {

    @Test
    public void shouldAddRepair() {
        //given
        Car car = new Car();

        //when
        car.addRepair(new Repair("Naprawa samochodu"));

        //then
        assertThat(car.getRepairs())
                .extracting(Repair::getRepairDescription).contains("Naprawa samochodu");
    }

    @Test
    public void testAddTestDrive() {
        //given
        Car car = new Car();

        //when
        car.addTestDrive(new TestDrive());

        //then
        assertThat(car.getTestDrives().size()).isGreaterThan(0);
    }
}