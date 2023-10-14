package com.bwagih.bank.management.system.validator;

import com.bwagih.bank.management.system.entity.ServiceAgreement;
import com.bwagih.bank.management.system.entity.Users;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordsEqualConstraintValidator implements
        ConstraintValidator<PasswordsEqualConstraint, Object> {

    @Override
    public void initialize(PasswordsEqualConstraint arg0) {
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {

        if (candidate instanceof ServiceAgreement) {
            ServiceAgreement req = new ServiceAgreement();
            return req.getPassword().equals(req.getConfirmPassword());

        } else if (candidate instanceof Users) {
            Users req = new Users();
            return req.getPassword().equals(req.getConfirmPassword());
        } else {
            return false;
        }

    }
}
