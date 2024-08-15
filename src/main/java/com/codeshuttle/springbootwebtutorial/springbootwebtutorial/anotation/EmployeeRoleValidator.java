package com.codeshuttle.springbootwebtutorial.springbootwebtutorial.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.modelmapper.internal.bytebuddy.asm.Advice;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation,String> {
    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext constraintValidatorContext) {
        if (inputRole==null) return false;
        List<String> roles =List.of("USER","ADMIN");
        return roles.contains(inputRole);
    }
}
