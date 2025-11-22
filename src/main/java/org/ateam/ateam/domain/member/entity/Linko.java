package org.ateam.ateam.domain.member.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ateam.ateam.domain.common.BaseEntity;
import org.ateam.ateam.domain.member.dto.req.LinkoProfileReqDTO;
import org.ateam.ateam.domain.member.enums.*;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

@Entity
@Table(name = "linko")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Linko extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false, unique = true)
    private Long memberId;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_category", nullable = false)
    private MainCategory mainCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_of_business", nullable = false)
    private CategoryOfBusiness categoryOfBusiness;

    @Column(name = "project_intro", columnDefinition = "TEXT", nullable = false)
    private String projectIntro;

    @Column(name = "expected_duration", nullable = false)
    private String expectedDuration;

    @Column(name = "expected_scope", nullable = false)
    private String expectedScope;

    @Enumerated(EnumType.STRING)
    @Column(name = "collaboration_type", nullable = false)
    private CollaborationType collaborationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private Region region;

    @Column(name = "deadline", nullable = false)
    private String deadline;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "linko_tech_stack", joinColumns = @JoinColumn(name = "linko_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tech_stack")
    private Set<TechStack> techStacks;

    @Builder
    public Linko(
            Long memberId,
            String companyName,
            CompanyType companyType,
            MainCategory mainCategory,
            CategoryOfBusiness categoryOfBusiness,
            String projectIntro,
            String expectedDuration,
            String expectedScope,
            CollaborationType collaborationType,
            Region region,
            String deadline,
            String requiredSkills) {
        this.memberId = memberId;
        this.companyName = companyName;
        this.companyType = companyType;
        this.mainCategory = mainCategory;
        this.categoryOfBusiness = categoryOfBusiness;
        this.projectIntro = projectIntro;
        this.expectedDuration = expectedDuration;
        this.expectedScope = expectedScope;
        this.collaborationType = collaborationType;
        this.region = region;
        this.deadline = deadline;
        this.requiredSkills = requiredSkills;
    }

    @PrePersist
    @PreUpdate
    private void validateCategory() {
        if (categoryOfBusiness.getMainCategory() != mainCategory) {
            throw new BusinessException(ErrorCode.CATEGORY_MISMATCH);
        }
    }

    // Linko.java에 update 메서드 추가
    public void update(LinkoProfileReqDTO dto) {
        this.companyName = dto.getCompanyName();
        this.companyType = dto.getCompanyType();
        this.mainCategory = dto.getMainCategory();
        this.categoryOfBusiness = dto.getCategoryOfBusiness();
        this.projectIntro = dto.getProjectIntro();
        this.expectedDuration = dto.getExpectedDuration();
        this.expectedScope = dto.getExpectedScope();
        this.collaborationType = dto.getCollaborationType();
        this.region = dto.getRegion();
        this.deadline = dto.getDeadline();
        this.requiredSkills = convertEnumListToJson(dto.getRequiredSkills());
    }

    private String convertEnumListToJson(List<TechStack> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (Exception e) {
            return "[]";
        }
    }
}
