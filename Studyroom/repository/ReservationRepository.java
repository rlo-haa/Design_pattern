package repository;

import model.Reservation;
import model.ReservationStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 예약 저장소
 */
public class ReservationRepository {
    private final Map<String, Reservation> reservations = new ConcurrentHashMap<>();

    /**
     * 예약 저장
     */
    public void save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    /**
     * 예약 업데이트
     */
    public void update(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    /**
     * 예약 ID로 검색
     */
    public Optional<Reservation> findById(String reservationId) {
        return Optional.ofNullable(reservations.get(reservationId));
    }

    /**
     * 회원 ID로 예약 목록 조회
     */
    public List<Reservation> findByMemberId(String memberId) {
        return reservations.values().stream()
                .filter(r -> r.getMember().getId().equals(memberId))
                .toList();
    }

    /**
     * 방 ID로 예약 목록 조회
     */
    public List<Reservation> findByRoomId(String roomId) {
        return reservations.values().stream()
                .filter(r -> r.getRoom().getId().equals(roomId))
                .toList();
    }

    /**
     * 전체 예약 목록 조회
     */
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    /**
     * 상태별 예약 조회
     */
    public List<Reservation> findByStatus(ReservationStatus status) {
        return reservations.values().stream()
                .filter(r -> r.getStatus() == status)
                .toList();
    }

    /**
     * 활성 예약 목록 조회
     */
    public List<Reservation> findActiveReservations() {
        return findByStatus(ReservationStatus.ACTIVE);
    }

    /**
     * 예약 삭제
     */
    public boolean delete(String reservationId) {
        return reservations.remove(reservationId) != null;
    }

    /**
     * 예약 개수 조회
     */
    public int count() {
        return reservations.size();
    }

    /**
     * 특정 상태의 예약 개수 조회
     */
    public long countByStatus(ReservationStatus status) {
        return reservations.values().stream()
                .filter(r -> r.getStatus() == status)
                .count();
    }

    /**
     * 회원별 예약 통계
     */
    public Map<String, Long> getReservationCountByMember() {
        return reservations.values().stream()
                .collect(Collectors.groupingBy(
                        r -> r.getMember().getId(),
                        Collectors.counting()
                ));
    }
}