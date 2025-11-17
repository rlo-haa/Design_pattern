package observer;

import model.Member;
import java.time.LocalDateTime;

/**
 * 예약 만료 이벤트
 */
public class ReservationExpiredEvent {
    private final String reservationId;
    private final Member member;
    private final String roomId;
    private final LocalDateTime expiredAt;

    public ReservationExpiredEvent(String reservationId, Member member, String roomId, LocalDateTime expiredAt) {
        this.reservationId = reservationId;
        this.member = member;
        this.roomId = roomId;
        this.expiredAt = expiredAt;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Member getMember() {
        return member;
    }

    public String getRoomId() {
        return roomId;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    @Override
    public String toString() {
        return String.format("ReservationExpiredEvent{id='%s', member='%s', room='%s', expiredAt=%s}",
                reservationId, member.getName(), roomId, expiredAt);
    }
}
