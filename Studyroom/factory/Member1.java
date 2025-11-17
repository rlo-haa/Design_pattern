// Member.java
public class Member {
    private String name;
    private String memberGrade;
    private String memberId;

    // 기본 생성자
    public Member() {
    }

    // 전체 파라미터 생성자
    public Member(String name, String memberGrade, String memberId) {
        this.name = name;
        this.memberGrade = memberGrade;
        this.memberId = memberId;
    }

    // Getter 메서드들
    public String getName() {
        return name;
    }

    public String getMemberGrade() {
        return memberGrade;
    }

    public String getMemberId() {
        return memberId;
    }

    // Setter 메서드들
    public void setName(String name) {
        this.name = name;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
