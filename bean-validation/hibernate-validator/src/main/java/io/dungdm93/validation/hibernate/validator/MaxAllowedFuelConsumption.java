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
@Constraint(validatedBy = {MaxAllowedFuelConsumption.MaxAllowedFuelConsumptionValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface MaxAllowedFuelConsumption {
    String message() default "{io.dungdm93.validation.hibernate.validator.MaxAllowedFuelConsumption.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MaxAllowedFuelConsumptionValidator implements ConstraintValidator<MaxAllowedFuelConsumption, Integer> {
        @Override
        public void initialize(MaxAllowedFuelConsumption annotation) {
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            return value == null || value >= 0 && value <= 10;
        }
    }
}