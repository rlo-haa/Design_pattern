package decorator;

import java.math.BigDecimal;

public class WhiteboardDecorator extends EquipmentDecorator {
    private static final BigDecimal PRICE = new BigDecimal("3000");

    public WhiteboardDecorator(ReservationComponent reservation) {
        super(reservation, "화이트보드", PRICE);
    }
}
