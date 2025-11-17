package model;

import java.math.BigDecimal;

/**
 * 방 등급 (Strategy 패턴에서 가격 계산에 사용)
 */
public enum RoomGrade {
    BRONZE(BigDecimal.ONE),
    SILVER(new BigDecimal("1.2")),
    PLATINUM(new BigDecimal("1.5"));

    private final BigDecimal priceMultiplier;

    RoomGrade(BigDecimal priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public BigDecimal getPriceMultiplier() {
        return priceMultiplier;
    }

    public String getDisplayName() {
        return switch (this) {
            case BRONZE -> "브론즈";
            case SILVER -> "실버";
            case PLATINUM -> "플래티넘";
        };
    }
}
