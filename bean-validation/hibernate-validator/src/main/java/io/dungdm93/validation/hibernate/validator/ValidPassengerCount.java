package io.dungdm93.validation.hibernate.validator;

import io.dungdm93.validation.hibernate.model.Car;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidPassengerCount.Validator.class})
@Documented
public @interface ValidPassengerCount {
    String message() default "{io.dungdm93.validation.hibernate.validator.ValidPassengerCount.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidPassengerCount, Car> {
        @Override
        public void initialize(ValidPassengerCount constraintAnnotation) {
        }

        @Override
        public boolean isValid(Car car, ConstraintValidatorContext context) {
            return car.seatCount >= car.getPassengers().size();
        }
    }
}