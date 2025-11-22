package org.ateam.ateam.domain.member.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import org.ateam.ateam.domain.member.annotation.validator.RateAmountValidator;

@Documented
@Constraint(validatedBy = RateAmountValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRateAmountSalary {
  String message() default "급여 범위가 유효하지 않습니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
