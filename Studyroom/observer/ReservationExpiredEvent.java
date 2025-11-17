package observer;

import account.Member;
import java.time.Instant;

/**
 * 예약 만료 시 발생하는 이벤트 객체
 * 만료된 Member와 Room 정보를 포함
 */
public final class ReservationExpiredEvent {
    private final Member member;
    private final String roomId;
    private final Instant expiredAt;

    public ReservationExpiredEvent(Member member, String roomId, Instant expiredAt) {
        this.member = member;
        this.roomId = roomId;
        this.expiredAt = expiredAt;
    }

    public Member getMember() {
        return member;
    }

    public String getRoomId() {
        return roomId;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    @Override
    public String toString() {
        return "ReservationExpiredEvent{" +
                "member=" + member.getName() +
                ", roomId='" + roomId + '\'' +
                ", expiredAt=" + expiredAt +
                '}';
    }
}