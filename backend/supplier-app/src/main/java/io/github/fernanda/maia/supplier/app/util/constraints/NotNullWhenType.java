package io.github.fernanda.maia.supplier.app.util.constraints;

import io.github.fernanda.maia.supplier.app.util.constraints.validators.NotNullWhenTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@Target(value = { ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = NotNullWhenTypeValidator.class)
@Repeatable(NotNullWhenType.List.class)
public @interface NotNullWhenType {

    String field();
    String expectedType();
    String typeFieldName();
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        NotNullWhenType[] value();
    }

}
