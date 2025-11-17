package service;

import java.math.BigDecimal;

import roominfo.Room;
import roominfo.RoomInventory;
import roominfo.RoomStatus;
import strategy.PricingStrategy;

public class RegisterService {
    private PricingStrategy strategy;
    private RoomInventory inventory;
    private RegistrationRepository registrationRepo;
    private ReservationScheduler scheduler;


    public RegisterService(PricingStrategy strategy, RoomInventory inventory,
                           RegistrationRepository registrationRepo, ReservationScheduler scheduler) {
        this.strategy = strategy;
        this.inventory = inventory;
        this.registrationRepo = registrationRepo;
        this.scheduler = scheduler;
    }


    public void setStrategy(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal quote(Registering registering) {
        // --- 데코레이터 로직 (추후 구현) ---
        // 1. 방 등급에 맞는 부가서비스 객체(Decorator) 생성
        // ReservationComponent services = createReservationServices(registering);

        // 2. 방 가격 계산 (Strategy)
        BigDecimal roomPrice = strategy.calculatePrice(registering);

        // --- 데코레이터 로직 (추후 구현) ---
        // 3. 부가 서비스 가격 계산 (Decorator)
        // BigDecimal servicePrice = services.getPrice();
        BigDecimal servicePrice = BigDecimal.ZERO; // 부가서비스가 없으므로 0으로 설정

        // (출력문 수정: servicePrice를 사용하지 않거나, 0으로 표시)
        System.out.println("[Service] 방 가격: " + roomPrice + ", 부가서비스: " + servicePrice);

        // (반환값 수정: roomPrice + servicePrice)
        return roomPrice.add(servicePrice); // servicePrice가 0이므로 roomPrice만 반환됨

        /* * [추후 복원] 데코레이터 구현 완료 시,
         * 1. servicePrice 계산 부분의 주석을 해제하고,
         * 2. 'BigDecimal.ZERO'를 'services.getPrice()'로 복원.
         * 3. 출력문(println)도 원래대로 복원.
         * 4. 최종 반환값(return)은 'roomPrice.add(servicePrice)'를 그대로 사용.
         */
    }

    public boolean register(Registering registering) {
        System.out.println("[Service] 등록 시도: " + registering.getMember().getName() + " -> " + registering.getRoom().getId());

        // 1. 재고 확인
        Room room = registering.getRoom();
        if (inventory.getRoomStatus(room.getId()) != RoomStatus.AVAILABLE) {
            System.out.println("[Service] 등록 실패: 방이 이용 불가능합니다.");
            return false;
        }

        // 2. 최종 가격 계산
        BigDecimal finalPrice = quote(registering);
        System.out.println("[Service] 최종 결제 금액: " + finalPrice);

        // 3. (결제 로직 - 생략)

        // 4. 등록
        registrationRepo.addRegistration(registering);

        // 5. 방 상태 변경 (Observer 발동)
        inventory.changeRoomStatus(room.getId(), RoomStatus.RESERVED);

        // 6. 만료 스케줄 등록 (Observer 발동)
        scheduler.scheduleExpiryNotification(registering);

        return true;
    }

    /**
     * 팩토리 선택 및 부가서비스 객체 생성 로직(추후 팩토리 구현 후 주석 해제)
    private ReservationComponent createReservationServices(Registering registering) {
        Room room = registering.getRoom();
        RoomGrade grade = room.getGrade();

        // 1. 등급에 맞는 Factory 선택
        ServiceFactory factory = selectFactoryByGrade(grade);

        // 2. 기본 예약 객체 생성
        BaseReservation baseReservation = new BaseReservation(room);

        // 3. Factory가 Decorator로 객체 조립
        return factory.CreateReservation(baseReservation);
    }

    private ServiceFactory selectFactoryByGrade(RoomGrade grade) {
        switch (grade) {
            case BRONZE:
                return new BronzeServiceFactory();
            case SILVER:
                return new SilverServiceFactory();
            case PLATINUM:
                return new PlatinumServiceFactory();
            default:
                throw new IllegalArgumentException("지원하지 않는 등급입니다.");
        }
    } */
}