// Registering.java
import java.time.LocalDateTime;

public class Registering {
    private Room room;
    private Member member;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // 기본 생성자
    public Registering() {
    }

    // 전체 파라미터 생성자
    public Registering(Room room, Member member, LocalDateTime startTime, LocalDateTime endTime) {
        this.room = room;
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // setServiceFactory 메서드
    public void setServiceFactory(ServiceFactory serviceFactory) {
        // ServiceFactory 설정
    }

    // Getter 메서드들
    public Room getRoom() {
        return room;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    // Setter 메서드들
    public void setRoom(Room room) {
        this.room = room;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}