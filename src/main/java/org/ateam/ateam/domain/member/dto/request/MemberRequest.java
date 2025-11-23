package org.ateam.ateam.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.member.annotation.ValidRateAmountSalary;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.LinkTingRole;

public class MemberRequest {

  @Builder
  public record ProfileListDTO(
      @Schema(description = "업종 (필수)", example = "LOGO_BRANDING")
          @NotNull(message = "업종은 비어있을 수 없습니다.")
          CategoryOfBusiness categoryOfBusiness,
      @Schema(description = "급여 기준 (주급/시급/월급)", example = "HOURLY")
          @NotNull(message = "급여 기준은 비어있을 수 없습니다.")
          RateUnit rateUnit,
      @Schema(description = "급여 금액 (입력된 기준과 금액으로 월 환산 금액을 계산하여 필터링)", example = "10000")
          @NotNull(message = "급여 금액은 비어있을 수 없습니다.")
          @ValidRateAmountSalary
          Integer rateAmount,
      @Schema(description = "역할 (LINKER 또는 LINKO)", example = "LINKER")
          @NotNull(message = "역할은 비어있을 수 없습니다.")
          LinkTingRole linkTingRole) {}

  @Builder
  public record TopListDTO(CategoryOfBusiness categoryOfBusiness) {}
}
