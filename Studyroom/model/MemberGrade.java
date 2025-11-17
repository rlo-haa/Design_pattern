package model;

/**
 * 회원 등급 (Factory 패턴에서 사용)
 */
public enum MemberGrade {
    BRONZE,
    SILVER,
    GOLD;

    public String getDisplayName() {
        return switch (this) {
            case BRONZE -> "브론즈";
            case SILVER -> "실버";
            case GOLD -> "골드";
        };
    }
}