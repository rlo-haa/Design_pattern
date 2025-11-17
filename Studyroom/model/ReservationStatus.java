package model;

/**
 * 예약 상태
 */
public enum ReservationStatus {
    PENDING,      // 결제 대기
    ACTIVE,       // 활성 예약
    COMPLETED,    // 완료된 예약
    CANCELLED,    // 취소된 예약
    EXPIRED;      // 만료된 예약

    public String getDisplayName() {
        return switch (this) {
            case PENDING -> "결제 대기";
            case ACTIVE -> "활성";
            case COMPLETED -> "완료";
            case CANCELLED -> "취소";
            case EXPIRED -> "만료";
        };
    }
}