package factory;

import decorator.ReservationComponent;
import model.Reservation;
import java.math.BigDecimal;

/**
 * 서비스 팩토리 인터페이스
 * 회원 등급에 따라 다른 장비 조합을 제공
 */
public interface ServiceFactory {
    ReservationComponent createReservation(Reservation reservation, BigDecimal basePrice);
}