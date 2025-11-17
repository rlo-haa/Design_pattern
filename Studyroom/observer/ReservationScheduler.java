package observer;

import model.Member;
import model.Reservation;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 예약 만료 스케줄러 및 Observer Subject
 */
public class ReservationScheduler {
    private final Map<String, ScheduledReservation> scheduledReservations = new ConcurrentHashMap<>();
    private final List<ReservationExpiryObserver> observers = new ArrayList<>();

    private static class ScheduledReservation {
        final String reservationId;
        final Member member;
        final String roomId;
        final LocalDateTime expiryTime;

        ScheduledReservation(String reservationId, Member member, String roomId, LocalDateTime expiryTime) {
            this.reservationId = reservationId;
            this.member = member;
            this.roomId = roomId;
            this.expiryTime = expiryTime;
        }
    }

    /**
     * 옵저버 등록
     */
    public void addObserver(ReservationExpiryObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    /**
     * 옵저버 제거
     */
    public void removeObserver(ReservationExpiryObserver observer) {
        observers.remove(observer);
    }

    /**
     * 모든 옵저버에게 알림 전송
     */
    private void notifyObservers(ReservationExpiredEvent event) {
        for (ReservationExpiryObserver observer : List.copyOf(observers)) {
            observer.onReservationExpired(event);
        }
    }

    /**
     * 예약 만료 알림 스케줄링
     */
    public void scheduleExpiryNotification(Reservation reservation) {
        ScheduledReservation scheduled = new ScheduledReservation(
                reservation.getId(),
                reservation.getMember(),
                reservation.getRoom().getId(),
                reservation.getEndTime()
        );
        scheduledReservations.put(reservation.getId(), scheduled);
    }

    /**
     * 예약 스케줄 취소
     */
    public void cancelSchedule(String reservationId) {
        scheduledReservations.remove(reservationId);
    }

    /**
     * 만료된 예약 확인 및 알림
     */
    public void checkAndNotify() {
        LocalDateTime now = LocalDateTime.now();
        List<String> expiredIds = new ArrayList<>();

        for (Map.Entry<String, ScheduledReservation> entry : scheduledReservations.entrySet()) {
            ScheduledReservation scheduled = entry.getValue();

            if (now.isAfter(scheduled.expiryTime)) {
                ReservationExpiredEvent event = new ReservationExpiredEvent(
                        scheduled.reservationId,
                        scheduled.member,
                        scheduled.roomId,
                        scheduled.expiryTime
                );
                notifyObservers(event);
                expiredIds.add(entry.getKey());
            }
        }

        expiredIds.forEach(scheduledReservations::remove);
    }

    /**
     * 현재 스케줄된 예약 개수
     */
    public int getScheduledCount() {
        return scheduledReservations.size();
    }
}
