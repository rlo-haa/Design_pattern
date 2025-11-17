package factory;

import decorator.*;
import model.Reservation;
import java.math.BigDecimal;

/**
 * 실버 등급 서비스 팩토리
 * 화이트보드 + 프로젝터 + 웹캠 제공
 */
public class SilverServiceFactory implements ServiceFactory {
    @Override
    public ReservationComponent createReservation(Reservation reservation, BigDecimal basePrice) {
        ReservationComponent component = new BasicReservation(reservation, basePrice);
        component = new WhiteboardDecorator(component);
        component = new ProjectorDecorator(component);
        component = new WebcamDecorator(component);
        return component;
    }
}