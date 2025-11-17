package service;

import account.Admin;
import model.Member;
import model.MemberGrade;
import repository.AccountRepository;

import java.util.List;

/**
 * 회원 관리 서비스
 */
public class MemberService {
    private final AccountRepository accountRepository;

    public MemberService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * 회원 등록
     */
    public void registerMember(Member member) {
        accountRepository.save(member);
        System.out.println("[MemberService] 회원 등록: " + member);
    }

    /**
     * 관리자 등록
     */
    public void registerAdmin(Admin admin) {
        accountRepository.save(admin);
        System.out.println("[MemberService] 관리자 등록: " + admin);
    }

    /**
     * 회원 조회
     */
    public Member getMember(String memberId) {
        return accountRepository.findById(memberId)
                .filter(account -> account instanceof Member)
                .map(account -> (Member) account)
                .orElse(null);
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> getAllMembers() {
        return accountRepository.findAllMembers();
    }

    /**
     * 회원 등급 업그레이드
     */
    public boolean upgradeMemberGrade(String memberId, MemberGrade newGrade) {
        Member member = getMember(memberId);
        if (member == null) {
            return false;
        }

        member.setGrade(newGrade);
        System.out.println("[MemberService] 회원 등급 변경: " + member.getName() + " → " + newGrade.getDisplayName());
        return true;
    }
}