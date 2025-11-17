package decorator;

import java.math.BigDecimal;

public class WebcamDecorator extends EquipmentDecorator {
    private static final BigDecimal PRICE = new BigDecimal("5000");

    public WebcamDecorator(ReservationComponent reservation) {
        super(reservation, "웹캠", PRICE);
    }
}