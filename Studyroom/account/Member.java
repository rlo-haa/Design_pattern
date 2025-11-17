package account;

import java.util.Objects;

/**
 * 회원 클래스
 * 회원 이름 및 연락처 정보 관리
 */
public final class Member extends Account {
    private final String name;
    private final String contact;

    public Member(String id, String password, String name, String contact) {
        super(id, password);
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.contact = Objects.requireNonNull(contact, "Contact cannot be null");
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }


    @Override
    public String getAccountType() {
        return "Member";
    }

    @Override
    public String toString() {
        return String.format("Member{id='%s', name='%s', contact='%s'}",
                getId(), name, contact);
    }
}