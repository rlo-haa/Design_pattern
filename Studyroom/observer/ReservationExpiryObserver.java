package observer;

/**
 * 예약 만료 이벤트를 감지하는 옵저버 인터페이스
 * ReservationScheduler로부터 알림을 받음
 */
public interface ReservationExpiryObserver {
    /**
     * 예약 만료 시 호출되는 메서드
     * @param event 예약 만료 이벤트 정보
     */
    void onReservationExpired(ReservationExpiredEvent event);
}