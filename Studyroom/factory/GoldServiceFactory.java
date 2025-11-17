// GoldServiceFactory.java
public class GoldServiceFactory implements ServiceFactory {
    private WhiteBoardDecorator whiteBoardDecorator;
    private ChargerDecorator chargerDecorator;
    private WebcamDecorator webcamDecorator;
    private ProjectorDecorator projectorDecorator;

    @Override
    public ReservationComponent createReservation(String baseReservation, Registering registering) {
        ReservationComponent component = new BaseReservation(baseReservation, registering);

        whiteBoardDecorator = new WhiteBoardDecorator(component);
        chargerDecorator = new ChargerDecorator(whiteBoardDecorator);
        webcamDecorator = new WebcamDecorator(chargerDecorator);
        projectorDecorator = new ProjectorDecorator(webcamDecorator);

        return projectorDecorator; // 데코레이터 반환
    }
}