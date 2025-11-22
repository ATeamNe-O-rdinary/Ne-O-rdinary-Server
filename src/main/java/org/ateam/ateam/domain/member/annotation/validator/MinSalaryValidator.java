package org.ateam.ateam.domain.member.annotation.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MinSalaryValidator implements ConstraintValidator<ExistFoods, List<Long>> {
}
