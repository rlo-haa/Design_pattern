package strategy;

import model.Reservation;
import java.math.BigDecimal;

/**
 * 가격 계산 전략 인터페이스
 */
public interface PricingStrategy {
    BigDecimal calculatePrice(Reservation reservation);
}