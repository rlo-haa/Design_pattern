package service;

import decorator.ReservationComponent;
import factory.ServiceFactory;
import factory.ServiceFactoryProvider;
import model.*;
import observer.RoomInventory;
import observer.ReservationScheduler;
import repository.ReservationRepository;
import strategy.DailyStrategy;
import strategy.FixedTermStrategy;
import strategy.PricingStrategy;

import java.math.BigDecimal;

/**
 * 예약 서비스
 * Strategy, Decorator, Factory, Observer 패턴을 모두 통합
 */
public class ReservationService {
    private PricingStrategy pricingStrategy;
    private final RoomInventory roomInventory;
    private final ReservationRepository reservationRepository;
    private final ReservationScheduler reservationScheduler;

    // 전략 객체들
    private final DailyStrategy dailyStrategy;
    private final FixedTermStrategy fixedTermStrategy;

    public ReservationService(RoomInventory roomInventory,
                              ReservationRepository reservationRepository,
                              ReservationScheduler reservationScheduler) {
        this.roomInventory = roomInventory;
        this.reservationRepository = reservationRepository;
        this.reservationScheduler = reservationScheduler;

        // 전략 초기화
        this.dailyStrategy = new DailyStrategy();
        this.fixedTermStrategy = new FixedTermStrategy(dailyStrategy);
        this.pricingStrategy = dailyStrategy; // 기본값
    }

    /**
     * 가격 전략 설정 (외부에서 지정 가능)
     */
    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    /**
     * 예약 기간에 따라 자동으로 전략 선택
     */
    private PricingStrategy selectStrategy(Reservation reservation) {
        long days = reservation.getDurationInDays();
        if (days >= 30) {
            System.out.println("[Service] 30일 이상 → FixedTermStrategy 사용");
            return fixedTermStrategy;
        } else {
            System.out.println("[Service] 30일 미만 → DailyStrategy 사용");
            return dailyStrategy;
        }
    }

    /**
     * 견적 계산 (실제 예약 없이 가격만 계산)
     */
    public BigDecimal calculateQuote(Reservation reservation) {
        // 1. 자동 전략 선택
        PricingStrategy strategy = selectStrategy(reservation);

        // 2. 방 가격 계산 (Strategy 패턴)
        BigDecimal roomPrice = strategy.calculatePrice(reservation);
        System.out.println("[Service] 방 가격: " + roomPrice);

        // 3. 회원 등급에 따른 부가 서비스 가격 계산 (Factory + Decorator 패턴)
        ServiceFactory factory = ServiceFactoryProvider.getFactory(reservation.getMember().getGrade());
        ReservationComponent component = factory.createReservation(reservation, roomPrice);

        BigDecimal totalPrice = component.getPrice();
        BigDecimal servicePrice = totalPrice.subtract(roomPrice);

        System.out.println("[Service] 부가서비스: " + servicePrice);
        System.out.println("[Service] 서비스 내역: " + component.getDescription());
        System.out.println("[Service] 최종 가격: " + totalPrice);

        return totalPrice;
    }

    /**
     * 예약 등록
     */
    public boolean register(Reservation reservation) {
        System.out.println("\n[Service] === 예약 등록 시작 ===");
        System.out.println("[Service] 회원: " + reservation.getMember().getName() +
                " (" + reservation.getMember().getGrade().getDisplayName() + ")");
        System.out.println("[Service] 방: " + reservation.getRoom().getId() +
                " (" + reservation.getRoom().getGrade().getDisplayName() + ")");
        System.out.println("[Service] 기간: " + reservation.getStartTime() + " ~ " + reservation.getEndTime());

        // 1. 방 이용 가능 여부 확인
        Room room = reservation.getRoom();
        RoomStatus status = roomInventory.getRoomStatus(room.getId());

        if (status != RoomStatus.AVAILABLE) {
            System.out.println("[Service] ❌ 등록 실패: 방이 이용 불가능합니다. (현재 상태: " + status.getDisplayName() + ")");
            return false;
        }

        // 2. 가격 계산
        BigDecimal finalPrice = calculateQuote(reservation);

        // 3. 결제 처리 (여기서는 생략)
        System.out.println("[Service] 💳 결제 처리: " + finalPrice + "원");

        // 4. 예약 상태를 ACTIVE로 변경하고 저장
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservationRepository.save(reservation);
        System.out.println("[Service] ✅ 예약 저장 완료: " + reservation.getId());

        // 5. 방 상태 변경 (Observer 패턴 - 상태 변경 알림 발생)
        roomInventory.changeRoomStatus(room.getId(), RoomStatus.RESERVED);

        // 6. 만료 스케줄 등록 (Observer 패턴 - 만료 알림 예약)
        reservationScheduler.scheduleExpiryNotification(reservation);
        System.out.println("[Service] 📅 만료 알림 스케줄 등록 완료");

        System.out.println("[Service] === 예약 등록 완료 ===\n");
        return true;
    }

    /**
     * 예약 취소
     */
    public boolean cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation == null) {
            System.out.println("[Service] 예약을 찾을 수 없습니다: " + reservationId);
            return false;
        }

        // 1. 예약 상태 변경
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.update(reservation);

        // 2. 방 상태 복구 (Observer 패턴)
        roomInventory.changeRoomStatus(reservation.getRoom().getId(), RoomStatus.AVAILABLE);

        // 3. 만료 스케줄 취소
        reservationScheduler.cancelSchedule(reservationId);

        System.out.println("[Service] 예약 취소 완료: " + reservationId);
        return true;
    }

    /**
     * 예약 조회
     */
    public Reservation getReservation(String reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }
}