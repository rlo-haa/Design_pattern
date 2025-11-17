package studyroom.decorator;

import java.math.BigDecimal;

public class DemoStudyRoomDecorator {
    public static void main(String[] args) {
        // 기본 룸
        ReservationComponent base = new BasicReservation("Standard", new BigDecimal("7000"));

        // 옵션 장비 추가
        ReservationComponent withProjector = new ProjectorDecorator(base);
        ReservationComponent withBoard = new WhiteboardDecorator(withProjector);
        ReservationComponent withWebcam = new WebcamDecorator(withBoard);

        // 결과 출력
        System.out.println("[스터디룸 예약 내역]");
        System.out.println("설명: " + withWebcam.getDescription());
        System.out.println("총 요금: " + withWebcam.getPrice()+"won");
    }
}