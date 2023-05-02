package io.github.fernanda.maia.supplier.app.util.constraints.validators;


import io.github.fernanda.maia.supplier.app.util.constraints.NotNullWhenType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;


public class NotNullWhenTypeValidator implements ConstraintValidator<NotNullWhenType, Object> {

    private String field;
    private String expectedType;
    private String typeFieldName;

    @Override
    public void initialize(NotNullWhenType annotation) {
        field = annotation.field();
        typeFieldName    = annotation.typeFieldName();
        expectedType = annotation.expectedType();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext ctx) {
        boolean isValidRequest = true;

        if(obj != null) {

            try {
                String dependFieldValue = BeanUtils.getProperty(obj, typeFieldName);
                String fieldValue = BeanUtils.getProperty(obj, field);

                isValidRequest = dependFieldValue != null && (!dependFieldValue.equals(expectedType) || fieldValue != null);

                if(!isValidRequest) {
                    ctx.disableDefaultConstraintViolation();
                    ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                            .addPropertyNode(field).addBeanNode().addConstraintViolation();
                }


            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }


        return isValidRequest;
    }
}
