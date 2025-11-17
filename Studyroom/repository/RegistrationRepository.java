package repository;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 예약 정보 저장소
 * 예약 정보 추가 및 조회 기능 관리
 */
public final class RegistrationRepository {

    /**
     * 예약 정보를 담는 내부 클래스
     */
    public static class Reservation {
        private final String reservationId;
        private final String memberId;
        private final String roomId;
        private final Instant startTime;
        private final Instant endTime;
        private ReservationStatus status;

        public Reservation(String reservationId, String memberId, String roomId,
                           Instant startTime, Instant endTime) {
            this.reservationId = reservationId;
            this.memberId = memberId;
            this.roomId = roomId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.status = ReservationStatus.ACTIVE;
        }

        public String getReservationId() { return reservationId; }
        public String getMemberId() { return memberId; }
        public String getRoomId() { return roomId; }
        public Instant getStartTime() { return startTime; }
        public Instant getEndTime() { return endTime; }
        public ReservationStatus getStatus() { return status; }

        public void setStatus(ReservationStatus status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("Reservation{id='%s', member='%s', room='%s', time=%s~%s, status=%s}",
                    reservationId, memberId, roomId, startTime, endTime, status);
        }
    }

    public enum ReservationStatus {
        ACTIVE,      // 활성 예약
        COMPLETED,   // 완료된 예약
        CANCELLED,   // 취소된 예약
        EXPIRED      // 만료된 예약
    }

    private final Map<String, Reservation> reservations = new ConcurrentHashMap<>();

    /**
     * 예약 추가
     */
    public void save(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
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
    public List<String> findReservationsByMemberId(String memberId) {
        return reservations.values().stream()
                .filter(r -> r.getMemberId().equals(memberId))
                .map(Reservation::getReservationId)
                .toList();
    }

    /**
     * 방 ID로 예약 목록 조회
     */
    public List<Reservation> findReservationsByRoomId(String roomId) {
        return reservations.values().stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();
    }

    /**
     * 전체 예약 목록 조회
     */
    public List<String> findAllReservations() {
        return new ArrayList<>(reservations.keySet());
    }

    /**
     * 활성 예약 목록 조회
     */
    public List<Reservation> findActiveReservations() {
        return reservations.values().stream()
                .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                .toList();
    }

    /**
     * 예약 상태 업데이트
     */
    public boolean updateStatus(String reservationId, ReservationStatus newStatus) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            return false;
        }
        reservation.setStatus(newStatus);
        return true;
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
}