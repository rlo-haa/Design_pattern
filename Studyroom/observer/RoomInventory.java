package observer;

import model.Room;
import model.RoomStatus;
import model.RoomGrade;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 방 재고 관리 및 Observer Subject
 */
public class RoomInventory {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final List<RoomAvailabilityObserver> observers = new ArrayList<>();

    /**
     * 옵저버 등록
     */
    public void addObserver(RoomAvailabilityObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    /**
     * 옵저버 제거
     */
    public void removeObserver(RoomAvailabilityObserver observer) {
        observers.remove(observer);
    }

    /**
     * 모든 옵저버에게 알림 전송
     */
    private void notifyObservers(String roomId, RoomStatus oldStatus, RoomStatus newStatus) {
        for (RoomAvailabilityObserver observer : List.copyOf(observers)) {
            observer.onRoomStatusChanged(roomId, oldStatus, newStatus);
        }
    }

    /**
     * 방 추가 (Room 객체)
     */
    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    /**
     * 방 추가 (ID만 - 하위 호환성, 기본 BRONZE 등급)
     */
    public void addRoom(String roomId) {
        Room room = new Room(roomId, roomId, RoomGrade.BRONZE, 4);
        rooms.put(roomId, room);
    }

    /**
     * 방 조회
     */
    public Optional<Room> getRoom(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    /**
     * 방 상태 조회
     */
    public RoomStatus getRoomStatus(String roomId) {
        return getRoom(roomId)
                .map(Room::getStatus)
                .orElse(null);
    }

    /**
     * 방 상태 변경 및 옵저버 알림
     */
    public boolean changeRoomStatus(String roomId, RoomStatus newStatus) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return false;
        }

        RoomStatus oldStatus = room.getStatus();
        if (oldStatus == newStatus) {
            return true;
        }

        room.setStatus(newStatus);
        notifyObservers(roomId, oldStatus, newStatus);
        return true;
    }

    /**
     * 특정 상태의 방 개수 조회
     */
    public long countByStatus(RoomStatus status) {
        return rooms.values().stream()
                .filter(r -> r.getStatus() == status)
                .count();
    }

    /**
     * 이용 가능한 방 목록 조회
     */
    public List<String> getAvailableRooms() {
        return rooms.values().stream()
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .map(Room::getId)
                .toList();
    }

    /**
     * 모든 방 정보 조회
     */
    public Collection<Room> getAllRooms() {
        return Collections.unmodifiableCollection(rooms.values());
    }
}