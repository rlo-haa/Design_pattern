import account.*;
import observer.*;
import repository.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * 스터디룸 시스템 데모
 * Observer 패턴과 Account 관리 기능 통합 테스트
 */
public class StudyroomDemo {
    public static void main(String[] args) {
        System.out.println("=== 스터디카페 시스템 시작 ===\n");

        // 1. Repository 초기화
        AccountRepository accountRepo = new AccountRepository();
        RegistrationRepository registrationRepo = new RegistrationRepository();

        // 2. 관리자 및 회원 계정 생성
        Admin admin = new Admin("admin", "admin123", accountRepo, registrationRepo);
        Member alice = new Member("alice", "pass1", "Alice Kim", "010-1111-2222");
        Member bob = new Member("bob", "pass2", "Bob Lee", "010-3333-4444");

        accountRepo.save(admin);
        accountRepo.save(alice);
        accountRepo.save(bob);

        System.out.println("--- 계정 등록 완료 ---");
        System.out.println("총 회원 수: " + accountRepo.countMembers());
        System.out.println();

        // 3. RoomInventory 및 옵저버 설정
        RoomInventory inventory = new RoomInventory();
        inventory.addObserver(new StudyroomDemoObserver());

        // 방 추가
        inventory.addRoom("A-101");
        inventory.addRoom("A-102");
        inventory.addRoom("B-201");

        System.out.println("--- 방 등록 완료 ---");
        System.out.println("이용 가능한 방: " + inventory.getAvailableRooms());
        System.out.println();

        // 4. ReservationScheduler 및 옵저버 설정
        ReservationScheduler scheduler = new ReservationScheduler();
        scheduler.addObserver(new MemberNotifier());

        // 5. 예약 생성 및 방 상태 변경
        System.out.println("--- 예약 프로세스 시작 ---");

        Instant now = Instant.now();
        Instant endTime = now.plus(2, ChronoUnit.HOURS);

        // Alice의 예약
        RegistrationRepository.Reservation res1 = new RegistrationRepository.Reservation(
                "RES-001", "alice", "A-101", now, endTime
        );
        registrationRepo.save(res1);
        inventory.changeRoomStatus("A-101", RoomStatus.OCCUPIED);
        scheduler.scheduleExpiryNotification("RES-001", alice, "A-101", endTime);

        System.out.println("Alice의 예약 생성: " + res1);
        System.out.println();

        // Bob의 예약
        RegistrationRepository.Reservation res2 = new RegistrationRepository.Reservation(
                "RES-002", "bob", "B-201", now, endTime
        );
        registrationRepo.save(res2);
        inventory.changeRoomStatus("B-201", RoomStatus.OCCUPIED);
        scheduler.scheduleExpiryNotification("RES-002", bob, "B-201", endTime);

        System.out.println("Bob의 예약 생성: " + res2);
        System.out.println();

        // 6. 관리자 기능 테스트
        System.out.println("--- 관리자 조회 기능 ---");
        System.out.println("전체 회원 수: " + admin.getTotalMemberCount());
        System.out.println("전체 예약 수: " + admin.getTotalReservationCount());
        System.out.println("Alice의 예약 목록: " + admin.getMemberReservations("alice"));
        System.out.println();

        // 7. 방 상태 변경 (이용 불가)
        System.out.println("--- 방 이용 불가 설정 ---");
        inventory.changeRoomStatus("A-102", RoomStatus.UNAVAILABLE);
        System.out.println();

        // 8. 예약 취소 및 방 상태 복구
        System.out.println("--- 예약 취소 ---");
        registrationRepo.updateStatus("RES-001", RegistrationRepository.ReservationStatus.CANCELLED);
        scheduler.cancelSchedule("RES-001");
        inventory.changeRoomStatus("A-101", RoomStatus.AVAILABLE);
        System.out.println("Alice의 예약 취소 완료");
        System.out.println();

        // 9. 예약 만료 체크 (시뮬레이션을 위해 과거 시간으로 설정)
        System.out.println("--- 예약 만료 체크 (시뮬레이션) ---");
        Instant pastTime = now.minus(1, ChronoUnit.HOURS);
        scheduler.scheduleExpiryNotification("RES-TEST", bob, "B-201", pastTime);
        scheduler.checkAndNotify();
        System.out.println();

        // 10. 최종 상태 확인
        System.out.println("--- 최종 상태 ---");
        System.out.println("이용 가능한 방: " + inventory.countByStatus(RoomStatus.AVAILABLE) + "개");
        System.out.println("사용 중인 방: " + inventory.countByStatus(RoomStatus.OCCUPIED) + "개");
        System.out.println("예약된 방: " + inventory.countByStatus(RoomStatus.RESERVED) + "개");
        System.out.println("이용 불가 방: " + inventory.countByStatus(RoomStatus.UNAVAILABLE) + "개");
        System.out.println("활성 예약: " + registrationRepo.countByStatus(
                RegistrationRepository.ReservationStatus.ACTIVE) + "개");
        System.out.println("취소된 예약: " + registrationRepo.countByStatus(
                RegistrationRepository.ReservationStatus.CANCELLED) + "개");

        System.out.println("\n=== 스터디카페 시스템 종료 ===");
    }
}