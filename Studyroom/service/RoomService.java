package service;

import model.Room;
import model.RoomStatus;
import observer.RoomInventory;

import java.util.Collection;
import java.util.List;

/**
 * 방 관리 서비스
 */
public class RoomService {
    private final RoomInventory roomInventory;

    public RoomService(RoomInventory roomInventory) {
        this.roomInventory = roomInventory;
    }

    /**
     * 방 추가
     */
    public void addRoom(Room room) {
        roomInventory.addRoom(room);
        System.out.println("[RoomService] 방 추가: " + room);
    }

    /**
     * 이용 가능한 방 목록 조회
     */
    public List<String> getAvailableRooms() {
        return roomInventory.getAvailableRooms();
    }

    /**
     * 방 상태 변경
     */
    public boolean changeRoomStatus(String roomId, RoomStatus newStatus) {
        return roomInventory.changeRoomStatus(roomId, newStatus);
    }

    /**
     * 모든 방 조회
     */
    public Collection<Room> getAllRooms() {
        return roomInventory.getAllRooms();
    }

    /**
     * 방 조회
     */
    public Room getRoom(String roomId) {
        return roomInventory.getRoom(roomId).orElse(null);
    }
}