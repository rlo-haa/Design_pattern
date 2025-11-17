package strategy;
import java.math.BigDecimal;

import roominfo.RoomGrade;
import service.Registering;

/**
 * 하이브리드 패키지 요금 전략.
 * (예: 7개월 = 6개월 패키지 + 1개월)
 */
public class FixedTermStrategy implements PricingStrategy {

    // 패키지 가격
    private static final BigDecimal ONE_MONTH_PRICE = new BigDecimal("250000"); // 1개월 예약 시
    private static final BigDecimal SIX_MONTHS_PRICE = new BigDecimal("1000000"); // 6개월 예약 시
    private static final BigDecimal TWELVE_MONTHS_PRICE = new BigDecimal("1500000");

    // 패키지 기준 일수 (1달=30일로 통일)
    private static final long ONE_MONTH_DAYS = 30;
    private static final long SIX_MONTHS_DAYS = 180;
    private static final long TWELVE_MONTHS_DAYS = 360;

    private final DailyStrategy fallbackStrategy; // 잔여일 계산을 위한 일일 요금 전략

    public FixedTermStrategy(DailyStrategy fallbackStrategy) {
        this.fallbackStrategy = (fallbackStrategy != null) ? fallbackStrategy : new DailyStrategy();
    }

    /**
     * 총 예약 일수에서 가장 큰 패키지를 적용하고,
     * 남은 일수는 DailyStrategy 요금으로 계산하여 합산.
     */
    @Override
    public BigDecimal calculatePrice(Registering registering) {
        long remainingDays = registering.getDurationInDays();
        BigDecimal totalPrice = BigDecimal.ZERO; // 총 패키지 가격

        System.out.println("[Strategy] 총 " + remainingDays + "일 계산 시작...");

        // 1. 12개월 패키지 적용 (360일)
        if (remainingDays >= TWELVE_MONTHS_DAYS) {
            long packageCount = remainingDays / TWELVE_MONTHS_DAYS;
            totalPrice = totalPrice.add(TWELVE_MONTHS_PRICE.multiply(BigDecimal.valueOf(packageCount)));
            remainingDays = remainingDays % TWELVE_MONTHS_DAYS; // 남은 일수 갱신
            System.out.println("[Strategy] 12개월 " + packageCount + "개 적용. (남은 일수: " + remainingDays + ")");
        }

        // 2. 6개월 패키지 적용 (180일)
        if (remainingDays >= SIX_MONTHS_DAYS) {
            long packageCount = remainingDays / SIX_MONTHS_DAYS; // 0 또는 1
            totalPrice = totalPrice.add(SIX_MONTHS_PRICE.multiply(BigDecimal.valueOf(packageCount)));
            remainingDays = remainingDays % SIX_MONTHS_DAYS;
            System.out.println("[Strategy] 6개월 " + packageCount + "개 적용. (남은 일수: " + remainingDays + ")");
        }

        // 3. 1개월 패키지 적용 (30일)
        if (remainingDays >= ONE_MONTH_DAYS) {
            long packageCount = remainingDays / ONE_MONTH_DAYS;
            totalPrice = totalPrice.add(ONE_MONTH_PRICE.multiply(BigDecimal.valueOf(packageCount)));
            remainingDays = remainingDays % ONE_MONTH_DAYS;
            System.out.println("[Strategy] 1개월 " + packageCount + "개 적용. (남은 일수: " + remainingDays + ")");
        }

        // 4. 남은 일수(Daily) 계산
        BigDecimal dailyRate = fallbackStrategy.getDailyRate(); // Bronze 기준 10,000원
        BigDecimal remainderPrice = dailyRate.multiply(BigDecimal.valueOf(remainingDays));
        System.out.println("[Strategy] 잔여일 " + remainingDays + "일 (" + remainderPrice + "원) 적용.");

        // 5. 패키지 총액 + 잔여일 총액 (Bronze 기준 가격)
        BigDecimal basePrice = totalPrice.add(remainderPrice);

        // 6. 계산된 총액에 등급 할증 적용
        RoomGrade grade = registering.getRoom().getGrade();
        BigDecimal finalPrice = basePrice; // 기본(Bronze)

        if (grade == RoomGrade.SILVER) {
            finalPrice = basePrice.multiply(new BigDecimal("1.2")); // 전체 20% 할증
        } else if (grade == RoomGrade.PLATINUM) {
            finalPrice = basePrice.multiply(new BigDecimal("1.5")); // 전체 50% 할증
        }

        return finalPrice;
    }
}