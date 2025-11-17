package roominfo;

public class Room {
    private String id;
    private RoomGrade grade;

    public Room(String id, RoomGrade grade) {
        this.id = id;
        this.grade = grade;
    }

    public String getId() { return this.id; }
    public RoomGrade getGrade() { return this.grade; }
}
