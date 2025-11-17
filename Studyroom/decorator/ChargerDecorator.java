package studyroom.decorator;

import java.math.BigDecimal;

class ChargerDecorator extends EquipmentDecorator {
    public ChargerDecorator(ReservationComponent reservation) {
        super(reservation, "노트북 충전기", new BigDecimal("2000"));
    }
}
