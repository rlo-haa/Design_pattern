package account;

import model.Member;
import repository.AccountRepository;
import repository.ReservationRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 계정
 * 회원 및 예약 목록 조회 기능 제공
 */
public final class Admin extends Account {
    private final AccountRepository accountRepository;
    private final ReservationRepository reservationRepository;

    public Admin(String id, String password,
                 AccountRepository accountRepository,
                 ReservationRepository reservationRepository) {
        super(id, password);
        this.accountRepository = accountRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public String getAccountType() {
        return "Admin";
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<Member> getAllMembers() {
        return accountRepository.findAll().stream()
                .filter(account -> account instanceof Member)
                .map(account -> (Member) account)
                .collect(Collectors.toList());
    }

    /**
     * 특정 회원 검색
     */
    public Member findMemberById(String memberId) {
        return accountRepository.findById(memberId)
                .filter(account -> account instanceof Member)
                .map(account -> (Member) account)
                .orElse(null);
    }

    /**
     * 특정 회원의 예약 목록 조회
     */
    public List<String> getMemberReservations(String memberId) {
        return reservationRepository.findByMemberId(memberId).stream()
                .map(r -> r.getId())
                .collect(Collectors.toList());
    }

    /**
     * 전체 예약 목록 조회
     */
    public List<String> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(r -> r.getId())
                .collect(Collectors.toList());
    }

    /**
     * 회원 통계 조회
     */
    public int getTotalMemberCount() {
        return getAllMembers().size();
    }

    /**
     * 예약 통계 조회
     */
    public int getTotalReservationCount() {
        return getAllReservations().size();
    }

    @Override
    public String toString() {
        return String.format("Admin{id='%s'}", getId());
    }
}