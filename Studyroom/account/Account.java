package account;

import java.util.Objects;

/**
 * 계정 추상 클래스
 * 공통 속성(id, password)과 로그인 기능을 정의
 */
public abstract class Account {
    private final String id;
    private final String password;

    protected Account(String id, String password) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.password = Objects.requireNonNull(password, "Password cannot be null");
    }

    public String getId() {
        return id;
    }

    protected String getPassword() {
        return password;
    }

    /**
     * 로그인 검증
     * @param inputPassword 입력된 비밀번호
     * @return 로그인 성공 여부
     */
    public boolean login(String inputPassword) {
        return password.equals(inputPassword);
    }

    /**
     * 계정 타입 반환 (Admin, Member 등)
     */
    public abstract String getAccountType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s{id='%s'}", getAccountType(), id);
    }
}