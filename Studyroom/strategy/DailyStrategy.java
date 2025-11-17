package strategy;

import model.Reservation;
import model.RoomGrade;
import java.math.BigDecimal;

/**
 * 일일 요금 계산 전략
 */
public class DailyStrategy implements PricingStrategy {
    private static final BigDecimal BASE_DAILY_RATE = new BigDecimal("10000");

    @Override
    public BigDecimal calculatePrice(Reservation reservation) {
        RoomGrade grade = reservation.getRoom().getGrade();
        long days = reservation.getDurationInDays();

        // 기본 요금 * 등급 배율 * 일수
        BigDecimal actualDailyRate = BASE_DAILY_RATE.multiply(grade.getPriceMultiplier());
        return actualDailyRate.multiply(BigDecimal.valueOf(days));
    }

    public BigDecimal getBaseDailyRate() {
        return BASE_DAILY_RATE;
    }
}