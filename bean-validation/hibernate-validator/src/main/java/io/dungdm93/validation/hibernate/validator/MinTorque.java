package io.dungdm93.validation.hibernate.validator;

import io.dungdm93.validation.hibernate.model.Gear;

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
@Constraint(validatedBy = {MinTorque.MinTorqueValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface MinTorque {
    long value();

    String message() default "{io.dungdm93.validation.hibernate.validator.MinTorque.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MinTorqueValidator implements ConstraintValidator<MinTorque, Gear> {
        private long min;

        @Override
        public void initialize(MinTorque annotation) {
            this.min = annotation.value();
        }

        @Override
        public boolean isValid(Gear gear, ConstraintValidatorContext context) {
            return gear == null || gear.getTorque() > min;
        }
    }
}
