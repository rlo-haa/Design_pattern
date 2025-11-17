package observer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 방 재고 및 상태를 관리하며, 상태 변경 시 옵저버들에게 알림을 전송하는 Subject
 */
public final class RoomInventory {
    private final Map<String, RoomStatus> rooms = new ConcurrentHashMap<>();
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
     * 방 추가 (초기 상태는 AVAILABLE)
     */
    public void addRoom(String roomId) {
        rooms.put(roomId, RoomStatus.AVAILABLE);
    }

    /**
     * 방 상태 변경 및 옵저버 알림
     * @return 상태 변경 성공 여부
     */
    public boolean changeRoomStatus(String roomId, RoomStatus newStatus) {
        RoomStatus oldStatus = rooms.get(roomId);
        if (oldStatus == null) {
            return false; // 존재하지 않는 방
        }

        if (oldStatus == newStatus) {
            return true; // 이미 동일한 상태
        }

        rooms.put(roomId, newStatus);
        notifyObservers(roomId, oldStatus, newStatus);
        return true;
    }

    /**
     * 방 상태 조회
     */
    public Optional<RoomStatus> getRoomStatus(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    /**
     * 특정 상태의 방 개수 조회
     */
    public long countByStatus(RoomStatus status) {
        return rooms.values().stream()
                .filter(s -> s == status)
                .count();
    }

    /**
     * 모든 방 정보 조회
     */
    public Map<String, RoomStatus> getAllRooms() {
        return Collections.unmodifiableMap(rooms);
    }

    /**
     * 이용 가능한 방 목록 조회
     */
    public List<String> getAvailableRooms() {
        return rooms.entrySet().stream()
                .filter(entry -> entry.getValue() == RoomStatus.AVAILABLE)
                .map(Map.Entry::getKey)
                .toList();
    }
}