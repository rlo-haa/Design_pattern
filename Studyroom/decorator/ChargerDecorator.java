package decorator;

import java.math.BigDecimal;

public class ChargerDecorator extends EquipmentDecorator {
    private static final BigDecimal PRICE = new BigDecimal("2000");

    public ChargerDecorator(ReservationComponent reservation) {
        super(reservation, "노트북 충전기", PRICE);
    }
}