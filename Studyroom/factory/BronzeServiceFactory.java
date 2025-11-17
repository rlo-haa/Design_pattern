// BronzeServiceFactory.java
public class BronzeServiceFactory implements ServiceFactory {
    private WhiteBoardDecorator decorator;

    @Override
    public ReservationComponent createReservation(String baseReservation, Registering registering) {
        ReservationComponent component = new BaseReservation(baseReservation, registering);
        decorator = new WhiteBoardDecorator(component);
        return decorator; //데코레이터 반환
    }
}