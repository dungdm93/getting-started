package io.dungdm93.validation.hibernate.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MinTowingCapacity.MinTowingCapacityValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface MinTowingCapacity {
    long value();

    String message() default "{io.dungdm93.validation.hibernate.validator.MinTowingCapacity.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MinTowingCapacityValidator implements ConstraintValidator<MinTowingCapacity, Integer> {
        private long min;

        @Override
        public void initialize(MinTowingCapacity annotation) {
            min = annotation.value();
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value == null || value >= min;
        }
    }
}