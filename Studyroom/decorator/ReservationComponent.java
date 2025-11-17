package decorator;

import java.math.BigDecimal;

/**
 * 예약 컴포넌트 인터페이스
 * Decorator 패턴의 Component 역할
 */
public interface ReservationComponent {
    BigDecimal getPrice();
    String getDescription();
}