package com.grturbo.grturbofullstackproject.model.validations;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstField;

    private String secondField;

    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory
                .forBeanPropertyAccess(value);

        Object firstValue = beanWrapper.getPropertyValue(this.firstField);
        Object secondValue = beanWrapper.getPropertyValue(this.secondField);

        boolean valid;

        if (firstValue == null) {
            valid = secondValue == null;
        } else {
            valid = firstValue.equals(secondValue);
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondField)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
