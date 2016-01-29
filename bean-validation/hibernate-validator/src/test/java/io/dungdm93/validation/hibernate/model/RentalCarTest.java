package io.dungdm93.validation.hibernate.model;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RentalCarTest extends CarTest {
    @Override
    public void setUp() {
        RentalCar rentalCar = new RentalCar("Morris", "DD-AB-123", 2);
        rentalCar.rentalStation = "Hanoi";
        this.car = rentalCar;
    }

    @Override
    public RentalCar car() {
        return (RentalCar) super.car();
    }

    @Test
    public void rentalStationIsNull() {
        car().rentalStation = null;

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }
}