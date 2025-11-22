package org.ateam.ateam.domain.linko.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.ateam.ateam.domain.linko.model.Linko;

@Getter
@Builder
public class LinkoCardResponse {

  private Long linkoId;
  private String companyName;
  private String companyImageUrl;
  private String category;

  public static LinkoCardResponse of(Linko linko) {
    return LinkoCardResponse.builder()
        .linkoId(linko.getId())
        .companyName(linko.getCompanyName())
        .companyImageUrl(linko.getCompanyImageUrl())
        .category(linko.getMainCategory().getTitle())
        .build();
  }
}
