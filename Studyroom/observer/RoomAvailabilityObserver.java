package observer;

/**
 * 방의 이용 가능 상태 변경을 감지하는 옵저버 인터페이스
 * RoomInventory로부터 알림을 수신
 */
public interface RoomAvailabilityObserver {
    /**
     * 방 상태 변경 이벤트 발생 시 호출됨
     * @param roomId 상태가 변경된 방 ID
     * @param oldStatus 이전 상태
     * @param newStatus 새로운 상태
     */
    void onRoomStatusChanged(String roomId, RoomStatus oldStatus, RoomStatus newStatus);
}