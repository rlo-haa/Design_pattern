package studyroom.decorator;

import java.math.BigDecimal;

class BasicReservation implements ReservationComponent {
    private final String roomType;
    private final BigDecimal basePrice;

    public BasicReservation(String roomType, BigDecimal basePrice) {
        this.roomType = roomType;
        this.basePrice = basePrice;
    }

    @Override
    public BigDecimal getPrice() {
        return basePrice;
    }

    @Override
    public String getDescription() {
        return "기본 스터디룸 (" + roomType + ")";
    }
}