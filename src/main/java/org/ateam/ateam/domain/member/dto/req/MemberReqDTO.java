package org.ateam.ateam.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ateam.ateam.domain.member.annotation.ValidMinSalary;

public class MemberReqDTO {

  @Builder
  public record ProfileListDTO(
      @Schema(description = "업종", example = "로고/브랜딩") @NotNull(message = "업종은 비어있을 수 없습니다.")
          String categoryOfBusiness,
      @Schema(description = "검색할 최소 급여 조건", example = "3000")
          @NotNull(message = "최소 급여는 비어있을 수 없습니다.")
          @ValidMinSalary
          Integer minSalary,
      @Schema(description = "검색할 최대 급여 조건", example = "8000")
          @NotNull(message = "최대 급여는 비어있을 수 없습니다.")
          Integer maxSalary) {}
}
