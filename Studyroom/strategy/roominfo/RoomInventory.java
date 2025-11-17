package roominfo;

public class RoomInventory {
    public RoomStatus getRoomStatus(String roomId) {
        return RoomStatus.AVAILABLE; // 데모 테스트용
    }

    public void changeRoomStatus(String roomId, RoomStatus status) {
        System.out.println("[Inventory] 방 상태 변경됨.");
    }
}
