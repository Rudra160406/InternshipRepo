package com.example.ems.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedCityValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedCity {

    String message() default "Employee from this city is not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
