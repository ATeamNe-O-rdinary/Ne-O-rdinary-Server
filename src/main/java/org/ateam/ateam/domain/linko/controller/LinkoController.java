package org.ateam.ateam.domain.linko.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linko.controller.response.LinkoCardResponse;
import org.ateam.ateam.domain.linko.service.LinkoQueryService;
import org.ateam.ateam.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/linkos")
@Tag(name = "Linko", description = "기업 카드 조회 API")
public class LinkoController {

  private final LinkoQueryService linkoQueryService;

  @Operation(summary = "링코 카드 목록 조회")
  @GetMapping("/cards")
  public ResponseDto<List<LinkoCardResponse>> getCards() {

    List<LinkoCardResponse> response = linkoQueryService.getCards();

    return ResponseDto.of(HttpStatus.OK, "LINKO_CARD_LIST_SUCCESS", "링코 카드 목록 조회 성공", response);
  }
}
