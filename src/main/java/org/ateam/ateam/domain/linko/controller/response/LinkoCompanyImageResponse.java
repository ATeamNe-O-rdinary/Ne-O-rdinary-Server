package org.ateam.ateam.domain.linko.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LinkoCompanyImageResponse {

  private String companyImageUrl;

  public static LinkoCompanyImageResponse from(String url) {
    return LinkoCompanyImageResponse.builder().companyImageUrl(url).build();
  }
}
