package io.github.fernanda.maia.supplier.app.util.constraints.validators;

import io.github.fernanda.maia.supplier.app.util.constraints.EnumAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumAvailableValidator implements ConstraintValidator<EnumAvailable, String> {

    private Set<String> availableNames;
    private boolean nullable;

    @Override
    public void initialize(EnumAvailable annotation) {
        this.nullable = annotation.nullable();
        Class<? extends Enum> classSelected = annotation.enumClass();
        availableNames = Arrays.stream(classSelected.getEnumConstants())
                .map(Enum::name).collect(Collectors.toSet());

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return availableNames.contains(value) || (nullable && value == null);
    }
}
