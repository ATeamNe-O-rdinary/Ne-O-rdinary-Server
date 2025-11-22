package org.ateam.ateam.domain.member.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.member.annotation.ValidRateAmountSalary;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateAmountValidator implements ConstraintValidator<ValidRateAmountSalary, Integer> {
  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    boolean isValid = (value >= 0);
    if (!isValid) {
      context.disableDefaultConstraintViolation();

      context
          .buildConstraintViolationWithTemplate(ErrorCode.INVALID_RATE_AMOUNT.getMessage())
          .addConstraintViolation();
    }

    return isValid;
  }
}
