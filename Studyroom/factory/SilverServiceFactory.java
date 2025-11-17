// SilverServiceFactory.java
public class SilverServiceFactory implements ServiceFactory {
    private WhiteBoardDecorator whiteBoardDecorator;
    private ProjectorDecorator projectorDecorator;
    private WebcamDecorator webcamDecorator;

    @Override
    public ReservationComponent createReservation(String baseReservation, Registering registering) {
        ReservationComponent component = new BaseReservation(baseReservation, registering);

        whiteBoardDecorator = new WhiteBoardDecorator(component);
        projectorDecorator = new ProjectorDecorator(whiteBoardDecorator);
        webcamDecorator = new WebcamDecorator(projectorDecorator);

        return webcamDecorator; // 데코레이터 반환
    }
}