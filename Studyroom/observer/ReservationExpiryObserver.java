package observer;

/**
 * 예약 만료 이벤트를 감지하는 옵저버 인터페이스
 */
public interface ReservationExpiryObserver {
    void onReservationExpired(ReservationExpiredEvent event);
}