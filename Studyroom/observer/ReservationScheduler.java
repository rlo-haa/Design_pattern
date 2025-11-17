package observer;

import account.Member;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 예약 만료를 감지하고 관련 옵저버에게 알림을 전송하는 Subject
 */
public final class ReservationScheduler {
    private final Map<String, ScheduledReservation> scheduledReservations = new ConcurrentHashMap<>();
    private final List<ReservationExpiryObserver> observers = new ArrayList<>();

    /**
     * 예약 정보를 담는 내부 클래스
     */
    private static class ScheduledReservation {
        final Member member;
        final String roomId;
        final Instant expiryTime;

        ScheduledReservation(Member member, String roomId, Instant expiryTime) {
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
     * @param reservationId 예약 ID
     * @param member 회원 정보
     * @param roomId 방 ID
     * @param expiryTime 만료 시각
     */
    public void scheduleExpiryNotification(String reservationId, Member member,
                                          String roomId, Instant expiryTime) {
        ScheduledReservation reservation = new ScheduledReservation(member, roomId, expiryTime);
        scheduledReservations.put(reservationId, reservation);
    }

    /**
     * 예약 스케줄 취소
     */
    public void cancelSchedule(String reservationId) {
        scheduledReservations.remove(reservationId);
    }

    /**
     * 만료된 예약을 확인하고 옵저버에게 알림
     * 주기적으로 호출되어야 하는 메서드
     */
    public void checkAndNotify() {
        Instant now = Instant.now();
        List<String> expiredIds = new ArrayList<>();

        for (Map.Entry<String, ScheduledReservation> entry : scheduledReservations.entrySet()) {
            ScheduledReservation reservation = entry.getValue();

            if (now.isAfter(reservation.expiryTime)) {
                // 만료된 예약 발견
                ReservationExpiredEvent event = new ReservationExpiredEvent(
                    reservation.member,
                    reservation.roomId,
                    reservation.expiryTime
                );

                notifyObservers(event);
                expiredIds.add(entry.getKey());
            }
        }

        // 만료된 예약 제거
        expiredIds.forEach(scheduledReservations::remove);
    }

    /**
     * 현재 스케줄된 예약 개수 조회
     */
    public int getScheduledCount() {
        return scheduledReservations.size();
    }
}