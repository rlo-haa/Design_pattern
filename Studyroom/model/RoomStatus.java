package model;

/**
 * 방 상태 (Observer 패턴에서 사용)
 */
public enum RoomStatus {
    AVAILABLE,    // 이용 가능
    OCCUPIED,     // 사용 중
    RESERVED,     // 예약됨
    UNAVAILABLE;  // 이용 불가

    public String getDisplayName() {
        return switch (this) {
            case AVAILABLE -> "이용 가능";
            case OCCUPIED -> "사용 중";
            case RESERVED -> "예약됨";
            case UNAVAILABLE -> "이용 불가";
        };
    }
}