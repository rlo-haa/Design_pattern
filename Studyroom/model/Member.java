package model;

import account.Account;
import java.util.Objects;

/**
 * 회원 도메인 모델 (통합)
 * account.Member를 확장하여 모든 패턴에서 사용
 */
public class Member extends Account {
    private final String name;
    private final String contact;
    private MemberGrade grade;

    public Member(String id, String password, String name, String contact, MemberGrade grade) {
        super(id, password);
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.contact = Objects.requireNonNull(contact, "Contact cannot be null");
        this.grade = Objects.requireNonNull(grade, "Grade cannot be null");
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
        this.grade = grade;
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