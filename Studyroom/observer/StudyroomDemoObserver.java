package observer;

/**
 * RoomAvailabilityObserver 인터페이스 구현체
 * 방 상태 변경을 콘솔에 출력
 */
public final class StudyroomDemoObserver implements RoomAvailabilityObserver {

    @Override
    public void onRoomStatusChanged(String roomId, RoomStatus oldStatus, RoomStatus newStatus) {
        String message = switch (newStatus) {
            case AVAILABLE -> String.format(
                    "[스터디룸 상태 변경] 방=%s, %s → %s (예약 가능)",
                    roomId, oldStatus, newStatus
            );
            case OCCUPIED -> String.format(
                    "[스터디룸 상태 변경] 방=%s, %s → %s (사용 중)",
                    roomId, oldStatus, newStatus
            );
            case RESERVED -> String.format(
                    "[스터디룸 상태 변경] 방=%s, %s → %s (예약됨)",
                    roomId, oldStatus, newStatus
            );
            case UNAVAILABLE -> String.format(
                    "[스터디룸 상태 변경] 방=%s, %s → %s (이용 불가)",
                    roomId, oldStatus, newStatus
            );
        };

        System.out.println(message);
    }
}