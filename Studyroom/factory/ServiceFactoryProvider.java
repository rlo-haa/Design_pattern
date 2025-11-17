package factory;

import model.MemberGrade;

/**
 * 팩토리 제공자
 * 회원 등급에 따라 적절한 팩토리를 반환
 */
public class ServiceFactoryProvider {
    public static ServiceFactory getFactory(MemberGrade grade) {
        return switch (grade) {
            case BRONZE -> new BronzeServiceFactory();
            case SILVER -> new SilverServiceFactory();
            case GOLD -> new GoldServiceFactory();
        };
    }
}