package com.example.ems.validation;

import com.example.ems.exception.InvalidCityException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AllowedCityValidator implements ConstraintValidator<AllowedCity, String> {

    private static final List<String> ALLOWED_CITIES =
            List.of("Ahmedabad", "Surat", "Vadodara","Gandhinagar");

    @Override
    public boolean isValid(String city, ConstraintValidatorContext context) {

        if (city == null || city.isBlank()) {
            return false;
        }

        if (!ALLOWED_CITIES.contains(city)) {
            throw new InvalidCityException(
                    "Employee from " + city + " city is not allowed"
            );
        }

        return true;
    }
}
