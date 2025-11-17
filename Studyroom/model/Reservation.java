package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

/**
 * 예약 도메인 모델 (통합)
 * Strategy, Factory, Decorator 패턴에서 공통 사용
 */
public class Reservation {
    private final String id;
    private final Member member;
    private final Room room;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private ReservationStatus status;

    public Reservation(Member member, Room room, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.member = Objects.requireNonNull(member, "Member cannot be null");
        this.room = Objects.requireNonNull(room, "Room cannot be null");
        this.startTime = Objects.requireNonNull(startTime, "Start time cannot be null");
        this.endTime = Objects.requireNonNull(endTime, "End time cannot be null");
        this.status = ReservationStatus.PENDING;

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * 예약 기간(일 단위)
     */
    public long getDurationInDays() {
        long days = ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate());
        return Math.max(days, 1);
    }

    /**
     * 예약 기간(시간 단위)
     */
    public long getDurationInHours() {
        return ChronoUnit.HOURS.between(startTime, endTime);
    }

    @Override
    public String toString() {
        return String.format("Reservation{id='%s', member='%s', room='%s', %s~%s, status=%s}",
                id, member.getName(), room.getId(), startTime, endTime, status);
    }
}
