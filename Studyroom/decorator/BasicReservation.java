package decorator;

import model.Reservation;
import java.math.BigDecimal;

/**
 * 기본 예약 컴포넌트
 * 장비 없이 방만 예약한 상태
 */
public class BasicReservation implements ReservationComponent {
    private final Reservation reservation;
    private final BigDecimal basePrice;

    public BasicReservation(Reservation reservation, BigDecimal basePrice) {
        this.reservation = reservation;
        this.basePrice = basePrice;
    }

    @Override
    public BigDecimal getPrice() {
        return basePrice;
    }

    @Override
    public String getDescription() {
        return String.format("기본 스터디룸 (%s, %s등급)",
                reservation.getRoom().getName(),
                reservation.getRoom().getGrade().getDisplayName());
    }

    public Reservation getReservation() {
        return reservation;
    }
}