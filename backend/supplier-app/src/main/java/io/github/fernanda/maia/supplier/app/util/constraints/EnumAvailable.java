package io.github.fernanda.maia.supplier.app.util.constraints;

import io.github.fernanda.maia.supplier.app.util.constraints.validators.EnumAvailableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@ReportAsSingleViolation
@Target(value = { ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER })
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumAvailableValidator.class)
public @interface EnumAvailable {
    Class<? extends Enum<?>> enumClass();

    boolean nullable() default false;
    String field() default "";
    String message() default "Enum Type is not available";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
