// Room.java
public class Room {
    private String roomName;
    private String roomType;
    private int capacity;

    // 기본 생성자
    public Room() {
    }

    // 전체 파라미터 생성자
    public Room(String roomName, String roomType, int capacity) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.capacity = capacity;
    }

    // Getter 메서드들
    public String getRoomName() {
        return roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setter 메서드들
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}