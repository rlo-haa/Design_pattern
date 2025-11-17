package strategy;
import roominfo.RoomGrade;
import service.Registering;
import java.math.BigDecimal;


// strategy.DailyStrategy.java
import java.math.BigDecimal;

public class DailyStrategy implements PricingStrategy {

    // 등급별 기본 일일 요금 (Bronze 기준)
    private BigDecimal baseDailyRate = new BigDecimal("10000");

    public DailyStrategy() {}

    public BigDecimal getDailyRate() {
        return this.baseDailyRate;
    }

    @Override
    public BigDecimal calculatePrice(Registering registering) {
        // 1. 등급(Grade) 가져오기
        RoomGrade grade = registering.getRoom().getGrade();

        // 2. 등급별 요금(Rate) 결정
        BigDecimal actualDailyRate = baseDailyRate; // 기본(Bronze)

        if (grade == RoomGrade.SILVER) {
            actualDailyRate = baseDailyRate.multiply(new BigDecimal("1.2")); // 12,000원
        } else if (grade == RoomGrade.PLATINUM) {
            actualDailyRate = baseDailyRate.multiply(new BigDecimal("1.5")); // 15,000원
        }

        // 3. 최종 가격 계산
        long days = registering.getDurationInDays();
        return actualDailyRate.multiply(BigDecimal.valueOf(days));
    }
}