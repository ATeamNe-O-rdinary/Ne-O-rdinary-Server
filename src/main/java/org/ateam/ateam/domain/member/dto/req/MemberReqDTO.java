package org.ateam.ateam.domain.member.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ateam.ateam.domain.member.annotation.ValidMinSalary;
import org.ateam.ateam.domain.member.enums.Gender;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public class MemberReqDTO {

    @Builder
    public record ProfileListDTO(
            @NotNull(message = "카테고리는 비어있을 수 없습니다.")
            String categoryOfBusiness,
            @NotNull(message = "최소 급여는 비어있을 수 없습니다.")
            @ValidMinSalary
            Integer minSalary,
            @NotNull(message = "최대 급여는 비어있을 수 없습니다.")
            Integer maxSalary,
            Pageable pageable
    ){

    }

}
