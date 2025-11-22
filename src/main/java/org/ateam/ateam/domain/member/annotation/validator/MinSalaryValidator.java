package org.ateam.ateam.domain.member.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.annotation.ValidMinSalary;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MinSalaryValidator implements ConstraintValidator<ValidMinSalary, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        boolean isValid = (value >= 0);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            // FoodErrorCode 대신 ErrorCode 사용
            context.buildConstraintViolationWithTemplate(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                    .addConstraintViolation();
        }

        return isValid;

    }
}
