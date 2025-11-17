package model;

import java.util.Objects;

/**
 * 방 도메인 모델 (통합)
 */
public class Room {
    private final String id;
    private final String name;
    private final RoomGrade grade;
    private final int capacity;
    private RoomStatus status;

    public Room(String id, String name, RoomGrade grade, int capacity) {
        this.id = Objects.requireNonNull(id, "Room ID cannot be null");
        this.name = Objects.requireNonNull(name, "Room name cannot be null");
        this.grade = Objects.requireNonNull(grade, "Room grade cannot be null");
        this.capacity = capacity;
        this.status = RoomStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RoomGrade getGrade() {
        return grade;
    }

    public int getCapacity() {
        return capacity;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Room{id='%s', name='%s', grade=%s, capacity=%d, status=%s}",
                id, name, grade, capacity, status);
    }
}