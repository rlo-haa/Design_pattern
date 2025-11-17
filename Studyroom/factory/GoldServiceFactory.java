package factory;

import decorator.*;
import model.Reservation;
import java.math.BigDecimal;

/**
 * 골드 등급 서비스 팩토리
 * 화이트보드 + 충전기 + 웹캠 + 프로젝터 제공 (풀패키지)
 */
public class GoldServiceFactory implements ServiceFactory {
    @Override
    public ReservationComponent createReservation(Reservation reservation, BigDecimal basePrice) {
        ReservationComponent component = new BasicReservation(reservation, basePrice);
        component = new WhiteboardDecorator(component);
        component = new ChargerDecorator(component);
        component = new WebcamDecorator(component);
        component = new ProjectorDecorator(component);
        return component;
    }
}
