import membership.*;
import roominfo.*;
import service.*;
import strategy.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StrategyDemo {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        RegistrationRepository repo = new RegistrationRepository();
        ReservationScheduler scheduler = new ReservationScheduler();

        System.out.println("--- 2. 가격 전략(Strategy) 생성 ---");
        // 테스트할 두 가지 전략 객체
        DailyStrategy dailyPolicy = new DailyStrategy();
        FixedTermStrategy fixedPolicy = new FixedTermStrategy(dailyPolicy);

        System.out.println("--- 3. RegisterService 생성 ---");
        // (기본 전략으로 DailyPolicy를 주입)
        RegisterService service = new RegisterService(dailyPolicy, inventory, repo, scheduler);

        // --- 4. 테스트용 객체 생성 ---
        Member member = new Member();
        Room silverRoom = new Room("R-201", RoomGrade.SILVER);
        Room platinumRoom = new Room("R-301", RoomGrade.PLATINUM);

        // =================================================================
        System.out.println("\n=== Test 1: 5일 단기 예약 (DailyStrategy) ===");
        LocalDateTime start1 = LocalDateTime.now();
        LocalDateTime end1 = start1.plusDays(5);
        Registering shortTerm = new Registering(silverRoom, member, start1, end1);

        // Demo가 예약 기간을 보고 전략을 결정
        if (shortTerm.getDurationInDays() < 30) {
            System.out.println("[Demo] 30일 미만, DailyStrategy 사용.");
            service.setStrategy(dailyPolicy);
        } else {
            System.out.println("[Demo] 30일 이상, FixedTermStrategy 사용.");
            service.setStrategy(fixedPolicy);
        }

        // 서비스 실행
        service.register(shortTerm);
        /*
         * [예상 결과]
         * [Service] 방 가격: 50000 (5 * 10000) * 1.2(RoomGrade = SILVER) = 60000
         * [Service] 최종 결제 금액: 60000
         */
        // =================================================================

        System.out.println("\n=== Test 2: 210일(7개월) 장기 예약 (FixedTermStrategy) ===");
        LocalDateTime start2 = LocalDateTime.now();
        LocalDateTime end2 = start2.plusDays(210); // 7개월(210일)
        Registering longTerm = new Registering(platinumRoom, member, start2, end2);

        // Demo가 예약 기간에 따른 전략을 결정
        if (longTerm.getDurationInDays() < 30) {
            System.out.println("[Demo] 30일 미만, DailyStrategy 사용.");
            service.setStrategy(dailyPolicy);
        } else {
            System.out.println("[Demo] 30일 이상, FixedTermStrategy 사용.");
            service.setStrategy(fixedPolicy);
        }

        // 서비스 실행
        service.register(longTerm);
        /*
         * [예상 결과] (210일 = 180일 + 30일)
         * [Service] 방 가격: 1250000 (100만 + 25만) * 1.5(RoomGrade = PLATINUM) = 1875000
         * [Service] 최종 결제 금액: 1875000
         */
        // =================================================================
    }
}