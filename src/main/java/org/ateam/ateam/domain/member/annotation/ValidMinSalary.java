package org.ateam.ateam.domain.member.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ateam.ateam.domain.member.annotation.validator.MinSalaryValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinSalaryValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMinSalary {
    String message() default "최소 급여가 유효하지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
