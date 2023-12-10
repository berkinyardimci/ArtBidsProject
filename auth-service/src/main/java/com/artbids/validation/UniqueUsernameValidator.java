package com.artbids.validation;

import com.artbids.repository.IAuthRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {


    private final IAuthRepository authRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return authRepository.findByUsername(value) == null;
    }
}
