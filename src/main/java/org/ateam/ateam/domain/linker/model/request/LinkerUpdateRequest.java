package org.ateam.ateam.domain.linker.model.request;

import java.util.List;

public record LinkerUpdateRequest(
    String nickname,
    String jobCategory,
    String careerLevel,
    String oneLineDescription,
    String workTimeType,
    String rateUnit,
    Integer rateAmount,
    String collaborationType,
    String region,
    List<String> techStacks) {}
