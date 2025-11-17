import java.math.BigDecimal;

// ReservationComponent.java
public interface ReservationComponent {
    BigDecimal getPrice();
    String getDescription();
    String getRoomInfo();
    void createService(String roomType, String roomGrade);
}