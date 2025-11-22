package org.ateam.ateam.domain.linker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.domain.linker.controller.response.LinkerResponse;
import org.ateam.ateam.domain.linker.model.request.LinkerCreateRequest;
import org.ateam.ateam.domain.linker.model.request.LinkerUpdateRequest;
import org.ateam.ateam.domain.linker.service.LinkerService;
import org.ateam.ateam.global.auth.context.UserContext;
import org.ateam.ateam.global.config.swagger.ApiErrorCodeExamples;
import org.ateam.ateam.global.dto.PagedResponse;
import org.ateam.ateam.global.dto.ResponseDto;
import org.ateam.ateam.global.error.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/linkers")
@Tag(name = "Linker Profile", description = "Linker 프로필 관리 API")
public class LinkerController {

	private final LinkerService linkerService;
	private final UserContext userContext;

	@Operation(summary = "링커 생성")
	@ApiErrorCodeExamples({
		ErrorCode.LINKER_ALREADY_EXISTS,
		ErrorCode.LINKER_INVALID_FIELD,
		ErrorCode.LINKER_INVALID_ENUM,
		ErrorCode.USER_NOT_FOUND
	})
	@PostMapping
	public ResponseDto<LinkerResponse> create(@RequestBody LinkerCreateRequest request) {

		Long memberId = userContext.getCurrentUserId();

		LinkerResponse response = linkerService.linkerCreate(memberId, request);

		return ResponseDto.of(
			HttpStatus.CREATED,
			"LINKER_CREATE_SUCCESS",
			"링커 정보 생성 성공",
			response
		);
	}

	@Operation(summary = "링커 단건 조회")
	@ApiErrorCodeExamples({
		ErrorCode.LINKER_NOT_FOUND
	})
	@GetMapping("/{linkerId}")
	public ResponseDto<LinkerResponse> getById(@PathVariable Long linkerId) {

		LinkerResponse response = linkerService.getById(linkerId);

		return ResponseDto.of(
			HttpStatus.OK,
			"LINKER_GET_SUCCESS",
			"링커 조회 성공",
			response
		);
	}

	@Operation(summary = "링커 페이징 목록 조회")
	@GetMapping
	public ResponseDto<PagedResponse<LinkerResponse>> getPage(Pageable pageable) {

		PagedResponse<LinkerResponse> response = linkerService.getPage(pageable);

		return ResponseDto.of(
			HttpStatus.OK,
			"LINKER_PAGE_SUCCESS",
			"링커 목록 조회 성공",
			response
		);
	}

	@Operation(summary = "링커 수정")
	@ApiErrorCodeExamples({
		ErrorCode.LINKER_NOT_FOUND,
		ErrorCode.LINKER_INVALID_FIELD,
		ErrorCode.LINKER_INVALID_ENUM,
		ErrorCode.USER_NOT_FOUND
	})
	@PutMapping("/{linkerId}")
	public ResponseDto<LinkerResponse> update(
		@PathVariable Long linkerId,
		@RequestBody LinkerUpdateRequest request
	) {

		Long memberId = userContext.getCurrentUserId();

		LinkerResponse response = linkerService.linkerUpdate(memberId, linkerId, request);

		return ResponseDto.of(
			HttpStatus.OK,
			"LINKER_UPDATE_SUCCESS",
			"링커 정보 수정 성공",
			response
		);
	}

	@Operation(summary = "링커 삭제")
	@ApiErrorCodeExamples({
		ErrorCode.LINKER_NOT_FOUND,
		ErrorCode.USER_NOT_FOUND
	})
	@DeleteMapping("/{linkerId}")
	public ResponseDto<Void> delete(@PathVariable Long linkerId) {

		Long memberId = userContext.getCurrentUserId();

		linkerService.linkerDelete(memberId, linkerId);

		return ResponseDto.of(
			HttpStatus.NO_CONTENT,
			"LINKER_DELETE_SUCCESS",
			"링커 삭제 성공"
		);
	}
}
