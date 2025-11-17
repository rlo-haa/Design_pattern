package factory;

import decorator.*;
import model.Reservation;
import java.math.BigDecimal;

/**
 * 브론즈 등급 서비스 팩토리
 * 화이트보드만 제공
 */
public class BronzeServiceFactory implements ServiceFactory {
    @Override
    public ReservationComponent createReservation(Reservation reservation, BigDecimal basePrice) {
        ReservationComponent component = new BasicReservation(reservation, basePrice);
        return new WhiteboardDecorator(component);
    }
}