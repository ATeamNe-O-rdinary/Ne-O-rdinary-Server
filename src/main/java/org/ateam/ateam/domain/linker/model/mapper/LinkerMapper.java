package org.ateam.ateam.domain.linker.model.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.ateam.ateam.domain.linker.controller.response.LinkerResponse;
import org.ateam.ateam.domain.linker.model.entity.Linker;
import org.ateam.ateam.domain.member.enums.TechStack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LinkerMapper {

  @Mapping(target = "memberId", source = "member.id")
  @Mapping(target = "mainCategory", source = "jobCategory.mainCategory.title")
  @Mapping(target = "jobCategory", source = "jobCategory.title")
  @Mapping(target = "careerLevel", source = "careerLevel.title")
  @Mapping(target = "workTimeType", source = "workTimeType.title")
  @Mapping(target = "rateUnit", source = "rateUnit.title")
  @Mapping(target = "collaborationType", source = "collaborationType.title")
  @Mapping(target = "region", source = "region.title")
  @Mapping(target = "techStacks", expression = "java(toTechStackTitles(linker))")
  LinkerResponse toResponse(Linker linker);

  default List<String> toTechStackTitles(Linker linker) {
    return linker.getTechStacks().stream().map(TechStack::getTitle).collect(Collectors.toList());
  }
}
