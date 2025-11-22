package org.ateam.ateam.domain.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.member.annotation.ValidMinSalary;
import org.ateam.ateam.domain.member.enums.CategoryOfBusiness;
import org.ateam.ateam.domain.member.enums.LinkTingRole;

public class MemberReqDTO {

  @Builder
  public record ProfileListDTO(
      @Schema(description = "업종", example = "LOGO_BRANDING") @NotNull(message = "업종은 비어있을 수 없습니다.")
      CategoryOfBusiness categoryOfBusiness,
      RateUnit rateUnit,
      Integer rateAmount,
      LinkTingRole linkTingRole
  ) {}

    @Builder
    public record TopListDTO(
            CategoryOfBusiness categoryOfBusiness
    ){}
}
