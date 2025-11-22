package org.ateam.ateam.domain.member.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ateam.ateam.global.error.ErrorCode;
import org.ateam.ateam.global.error.exception.BusinessException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CategoryOfBusiness {

    // === 디자인 ===
    LOGO_BRANDING("로고/브랜딩", MainCategory.DESIGN),
    WEB_APP_BANNER("웹/앱/배너", MainCategory.DESIGN),
    CHARACTER_DESIGN("캐릭터디자인", MainCategory.DESIGN),
    PACKAGE_PACKAGING("패키지/포장", MainCategory.DESIGN),
    DESIGN_ETC("기타", MainCategory.DESIGN),

    // === 마케팅 ===
    SNS_OPERATION("SNS 운영", MainCategory.MARKETING),
    CONTENT_CREATION("콘텐츠제작", MainCategory.MARKETING),
    PERFORMANCE_AD("퍼포먼스 광고", MainCategory.MARKETING),

    // === IT/프로그래밍 ===
    WEB_DEV("웹제작", MainCategory.IT_PROGRAMMING),
    APP_DEV("앱제작", MainCategory.IT_PROGRAMMING),
    GAME_DEV("게임개발", MainCategory.IT_PROGRAMMING),
    AI_DEV("AI", MainCategory.IT_PROGRAMMING),
    SERVER_SETUP("서버구축", MainCategory.IT_PROGRAMMING);

    private final String title;
    private final MainCategory mainCategory;

    public static CategoryOfBusiness from(String value) {
        return Arrays.stream(CategoryOfBusiness.values())
                .filter(category -> category.getTitle().equals(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
