package observer;

import model.RoomStatus;

/**
 * 방 상태 변경을 로깅하는 옵저버
 */
public class RoomStatusLogger implements RoomAvailabilityObserver {
    @Override
    public void onRoomStatusChanged(String roomId, RoomStatus oldStatus, RoomStatus newStatus) {
        System.out.printf("[방 상태 변경] 방=%s, %s → %s%n",
                roomId, oldStatus.getDisplayName(), newStatus.getDisplayName());
    }
}