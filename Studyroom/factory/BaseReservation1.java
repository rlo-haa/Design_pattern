import java.math.BigDecimal;

// BaseReservation.java
public class BaseReservation implements ReservationComponent {
    private BigDecimal price;
    private String description;
    private String roomInfo;
    private Registering registering; // Registering 참조

    public BaseReservation(String reservationType, Registering registering) {
        this.description = reservationType;
        this.price = BigDecimal.ZERO;
        this.registering = registering;

        // Registering 정보를 활용하여 서비스 생성
        if (registering != null && registering.getRoom() != null && registering.getMember() != null) {
            createService(registering.getRoom().getRoomType(),
                    registering.getMember().getMemberGrade());
        }
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getRoomInfo() {
        return roomInfo;
    }

    @Override
    public void createService(String roomType, String roomGrade) {
        this.roomInfo = "Room Type: " + roomType + ", Grade: " + roomGrade;
        if (registering != null) {
            this.roomInfo += String.format(" | %s ~ %s",
                    registering.getStartTime(),
                    registering.getEndTime());
        }
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}