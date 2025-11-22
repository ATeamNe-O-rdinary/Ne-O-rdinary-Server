package org.ateam.ateam.domain.linko.controller.response;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.ateam.ateam.domain.linker.model.enums.RateUnit;
import org.ateam.ateam.domain.linko.model.Linko;
import org.ateam.ateam.domain.member.enums.*;

@Getter
@Builder
public class LinkoProfileResDTO {
    private Long id;
    private String companyName;
    private CompanyType companyType;
    private MainCategory mainCategory;
    private CategoryOfBusiness categoryOfBusiness;
    private String projectIntro;
    private ExpectedDuration expectedDuration;
    private RateUnit rateUnit;
    private Integer rateAmount;
    private CollaborationType collaborationType;
    private Region region;
    private String deadline;
    private Set<TechStack> techStacks;

    public static LinkoProfileResDTO from(Linko linko) {
        return LinkoProfileResDTO.builder()
                .id(linko.getId())
                .companyName(linko.getCompanyName())
                .companyType(linko.getCompanyType())
                .mainCategory(linko.getMainCategory())
                .categoryOfBusiness(linko.getCategoryOfBusiness())
                .projectIntro(linko.getProjectIntro())
                .expectedDuration(linko.getExpectedDuration())
                .rateUnit(linko.getRateUnit())
                .rateAmount(linko.getRateAmount())
                .collaborationType(linko.getCollaborationType())
                .region(linko.getRegion())
                .deadline(linko.getDeadline())
                .techStacks(linko.getTechStacks())
                .build();
    }
}