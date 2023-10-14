package com.bwagih.bank.management.system.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
public @interface PasswordsEqualConstraint {
    String message() default "Confirm Password should be equals Password field.. ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
