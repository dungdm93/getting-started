package io.dungdm93.validation.hibernate.model;

import io.dungdm93.validation.hibernate.constant.FuelConsumption;
import io.dungdm93.validation.hibernate.validator.GearBoxUnwrapper;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CarTest {

    protected static Validator validator;
    protected Car car;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addValidatedValueHandler(new GearBoxUnwrapper())
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setUp() {
        if (Objects.isNull(car)) car = new Car();
        car.manufacturer = "Morris";
        car.licensePlate = "DD-AB-123";
        car.seatCount = 2;
        car.driver = new Person("Jason Statham");
    }

    public Car car() {
        return car;
    }

    @Test
    public void manufacturerIsNull() {
        car().manufacturer = null;

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void licensePlateTooShort() {
        car().licensePlate = "D";

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 2 and 14",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void seatCountTooLow() {
        car().seatCount = 1;

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be greater than or equal to 2",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void carIsNotRegistered() {
        car().setRegistered(false);

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("must be true",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void carIsValid() {
        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void carHasInvalidPart() {
        car().addPart("Wheel");
        car().addPart(null);

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("'null' is not a valid element of collection.",
                constraintViolations.iterator().next().getMessage()
        );
        assertEquals("parts[1]", constraintViolations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void carHasInvalidFuelConsumption() {
        car().setFuelConsumption(FuelConsumption.HIGHWAY, 20);

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("20 is outside the max fuel consumption.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void invalidTowingCapacity() {
        car().setTowingCapacity(100);

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("Not enough towing capacity.", constraintViolations.iterator().next().getMessage());
        assertEquals("towingCapacity", constraintViolations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void invalidGearBox() {
        car().setGearBox(new GearBox<>(new Gear.AcmeGear()));

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("Gear is not providing enough torque.", constraintViolations.iterator().next().getMessage());
        assertEquals("gearBox", constraintViolations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void tooMuchPassengers() {
        car().addPassenger(new Person("A"));
        car().addPassenger(new Person("B"));
        car().addPassenger(new Person("C"));
        car().addPassenger(new Person("D"));
        car().addPassenger(new Person("E"));

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("Too much passenger in your car.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void hasInvalidPassengers() {
        car().addPassenger(new Person()); // null name
        car().addPassenger(null);

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);

        assertEquals(2, constraintViolations.size());
        Iterator<ConstraintViolation<Car>> it = constraintViolations.iterator();

        ConstraintViolation<Car> first = it.next();
        assertEquals("may not be null", first.getMessage());
        assertEquals("passengers[0].name", first.getPropertyPath().toString());

        ConstraintViolation<Car> second = it.next();
        assertEquals("'null' is not a valid element of collection.", second.getMessage());
    }
}
