package io.dungdm93.validation.hibernate.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {NotNullElement.NotNullElementValidator.class})
@Target(TYPE_USE)
@Retention(RUNTIME)
public @interface NotNullElement {
    String message() default "{io.dungdm93.validation.hibernate.validator.NotNullElement.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NotNullElementValidator implements ConstraintValidator<NotNullElement, Object> {
        @Override
        public void initialize(NotNullElement annotation) {
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return value != null;
        }
    }
}