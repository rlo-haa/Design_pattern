package service;
import membership.Member;
import roominfo.Room;

import java.time.LocalDateTime;

public class Registering {
    private Room room;
    private Member member;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public Registering(Room room, Member member, LocalDateTime startTime, LocalDateTime endTime) {
        this.room = room;
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Room getRoom() { return room; }
    public Member getMember() { return member; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    public long getDurationInDays() {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate());
        return Math.max(days, 1);
    }
}