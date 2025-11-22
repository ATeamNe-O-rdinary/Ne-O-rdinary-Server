package org.ateam.ateam.domain.linker.controller.response;

import java.util.List;

public record LinkerResponse(
	Long id,
	Long memberId,
	String nickname,
	String mainCategory,
	String jobCategory,
	String careerLevel,
	String oneLineDescription,
	String workTimeType,
	String rateUnit,
	Integer rateAmount,
	String collaborationType,
	String region,
	List<String> techStacks
) {}
