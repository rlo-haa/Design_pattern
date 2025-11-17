package strategy;

import model.Reservation;
import model.RoomGrade;
import java.math.BigDecimal;

/**
 * 장기 예약 패키지 요금 계산 전략
 * 30일 이상 예약 시 사용
 */
public class FixedTermStrategy implements PricingStrategy {
    // 패키지 가격 (BRONZE 기준)
    private static final BigDecimal ONE_MONTH_PRICE = new BigDecimal("250000");
    private static final BigDecimal SIX_MONTHS_PRICE = new BigDecimal("1000000");
    private static final BigDecimal TWELVE_MONTHS_PRICE = new BigDecimal("1500000");

    // 패키지 기준 일수
    private static final long ONE_MONTH_DAYS = 30;
    private static final long SIX_MONTHS_DAYS = 180;
    private static final long TWELVE_MONTHS_DAYS = 360;

    private final DailyStrategy fallbackStrategy;

    public FixedTermStrategy(DailyStrategy fallbackStrategy) {
        this.fallbackStrategy = fallbackStrategy != null ? fallbackStrategy : new DailyStrategy();
    }

    @Override
    public BigDecimal calculatePrice(Reservation reservation) {
        long remainingDays = reservation.getDurationInDays();
        BigDecimal totalPrice = BigDecimal.ZERO;

        System.out.println("[FixedTermStrategy] 총 " + remainingDays + "일 계산 시작...");

        // 1. 12개월 패키지 적용
        if (remainingDays >= TWELVE_MONTHS_DAYS) {
            long packageCount = remainingDays / TWELVE_MONTHS_DAYS;
            totalPrice = totalPrice.add(TWELVE_MONTHS_PRICE.multiply(BigDecimal.valueOf(packageCount)));
            remainingDays = remainingDays % TWELVE_MONTHS_DAYS;
            System.out.println("[FixedTermStrategy] 12개월 " + packageCount + "개 적용 (남은 일수: " + remainingDays + ")");
        }

        // 2. 6개월 패키지 적용
        if (remainingDays >= SIX_MONTHS_DAYS) {
            long packageCount = remainingDays / SIX_MONTHS_DAYS;
            totalPrice = totalPrice.add(SIX_MONTHS_PRICE.multiply(BigDecimal.valueOf(packageCount)));
            remainingDays = remainingDays % SIX_MONTHS_DAYS;
            System.out.println("[FixedTermStrategy] 6개월 " + packageCount + "개 적용 (남은 일수: " + remainingDays + ")");
        }

        // 3. 1개월 패키지 적용
        if (remainingDays >= ONE_MONTH_DAYS) {
            long packageCount = remainingDays / ONE_MONTH_DAYS;
            totalPrice = totalPrice.add(ONE_MONTH_PRICE.multiply(BigDecimal.valueOf(packageCount)));
            remainingDays = remainingDays % ONE_MONTH_DAYS;
            System.out.println("[FixedTermStrategy] 1개월 " + packageCount + "개 적용 (남은 일수: " + remainingDays + ")");
        }

        // 4. 남은 일수는 일일 요금으로 계산
        if (remainingDays > 0) {
            BigDecimal dailyRate = fallbackStrategy.getBaseDailyRate();
            BigDecimal remainderPrice = dailyRate.multiply(BigDecimal.valueOf(remainingDays));
            totalPrice = totalPrice.add(remainderPrice);
            System.out.println("[FixedTermStrategy] 잔여일 " + remainingDays + "일 (" + remainderPrice + "원) 적용");
        }

        // 5. 등급 할증 적용
        RoomGrade grade = reservation.getRoom().getGrade();
        BigDecimal finalPrice = totalPrice.multiply(grade.getPriceMultiplier());

        System.out.println("[FixedTermStrategy] 기본 가격: " + totalPrice + ", 등급 배율: " + grade.getPriceMultiplier() + ", 최종: " + finalPrice);

        return finalPrice;
    }
}