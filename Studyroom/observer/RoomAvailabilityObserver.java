package observer;

import model.RoomStatus;

/**
 * 방 상태 변경을 감지하는 옵저버 인터페이스
 */
public interface RoomAvailabilityObserver {
    void onRoomStatusChanged(String roomId, RoomStatus oldStatus, RoomStatus newStatus);
}