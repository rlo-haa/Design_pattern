package model;

import account.Account;
import java.util.Objects;

/**
 * 회원 도메인 모델
 * account.Account를 상속받아 인증 기능 포함
 */
public class Member extends Account {
    private final String name;
    private final String contact;
    private MemberGrade grade;

    /**
     * 전체 파라미터 생성자
     */
    public Member(String id, String password, String name, String contact, MemberGrade grade) {
        super(id, password);
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.contact = Objects.requireNonNull(contact, "Contact cannot be null");
        this.grade = Objects.requireNonNull(grade, "Grade cannot be null");
    }

    /**
     * 기본 등급(BRONZE)으로 회원 생성
     */
    public Member(String id, String password, String name, String contact) {
        this(id, password, name, contact, MemberGrade.BRONZE);
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public MemberGrade getGrade() {
        return grade;
    }

    public void setGrade(MemberGrade grade) {
        this.grade = Objects.requireNonNull(grade, "Grade cannot be null");
    }

    @Override
    public String getAccountType() {
        return "Member";
    }

    @Override
    public String toString() {
        return String.format("Member{id='%s', name='%s', contact='%s', grade=%s}",
                getId(), name, contact, grade);
    }
}