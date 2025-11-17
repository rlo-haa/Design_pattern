package observer;

/**
 * ReservationExpiryObserver 인터페이스 구현체
 * 예약 만료 시 회원에게 알림을 전송
 */
public final class MemberNotifier implements ReservationExpiryObserver {

    @Override
    public void onReservationExpired(ReservationExpiredEvent event) {
        // 실제로는 SMS, 이메일, 푸시 알림 등을 전송
        System.out.printf(
                "[예약 만료 알림] 회원=%s, 연락처=%s, 방=%s, 만료시각=%s%n",
                event.getMember().getName(),
                event.getMember().getContact(),
                event.getRoomId(),
                event.getExpiredAt()
        );

        // 실제 알림 전송 로직
        sendNotification(event);
    }

    /**
     * 실제 알림 전송 (예시)
     */
    private void sendNotification(ReservationExpiredEvent event) {
        String message = String.format(
                "%s님, %s 방의 예약이 만료되었습니다.",
                event.getMember().getName(),
                event.getRoomId()
        );

        // TODO: 실제 알림 전송 구현
        System.out.println("[알림 전송] " + message);
    }
}