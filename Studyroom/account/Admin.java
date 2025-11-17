package account;

import repository.AccountRepository;
import repository.RegistrationRepository;
import java.util.List;

/**
 * 관리자 계정
 * 회원 및 예약 목록 조회 기능 제공
 */
public final class Admin extends Account {
    private final AccountRepository accountRepository;
    private final RegistrationRepository registrationRepository;

    public Admin(String id, String password,
                 AccountRepository accountRepository,
                 RegistrationRepository registrationRepository) {
        super(id, password);
        this.accountRepository = accountRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public String getAccountType() {
        return "Admin";
    }

    /**
     * 전체 회원 목록 조회
     */
    public List<Member> getAllMembers() {
        return accountRepository.findAllMembers();
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
        return registrationRepository.findReservationsByMemberId(memberId);
    }

    /**
     * 전체 예약 목록 조회
     */
    public List<String> getAllReservations() {
        return registrationRepository.findAllReservations();
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