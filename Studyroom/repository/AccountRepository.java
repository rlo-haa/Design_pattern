package repository;

import account.Account;
import model.Member;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 계정 저장소
 */
public final class AccountRepository {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    /**
     * 계정 추가
     */
    public void save(Account account) {
        accounts.put(account.getId(), account);
    }

    /**
     * ID로 계정 검색
     */
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /**
     * 전체 회원 목록 조회 (model.Member만 필터링)
     */
    public List<Member> findAllMembers() {
        return accounts.values().stream()
                .filter(account -> account instanceof Member)
                .map(account -> (Member) account)
                .toList();
    }

    /**
     * 전체 계정 목록 조회
     */
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    /**
     * 계정 삭제
     */
    public boolean delete(String id) {
        return accounts.remove(id) != null;
    }

    /**
     * 계정 존재 여부 확인
     */
    public boolean exists(String id) {
        return accounts.containsKey(id);
    }

    /**
     * 계정 개수 조회
     */
    public int count() {
        return accounts.size();
    }

    /**
     * 회원 개수 조회
     */
    public int countMembers() {
        return (int) accounts.values().stream()
                .filter(account -> account instanceof Member)
                .count();
    }
}