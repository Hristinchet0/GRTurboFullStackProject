package com.grturbo.grturbofullstackproject.model.validations;


import com.grturbo.grturbofullstackproject.repositority.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    private UserRepository userRepository;

    public UniqueUserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return userRepository.
                findByEmail(value).
                isEmpty();
    }
}