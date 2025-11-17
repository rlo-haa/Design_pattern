package studyroom.decorator;

import java.math.BigDecimal;

class WhiteboardDecorator extends EquipmentDecorator {
    public WhiteboardDecorator(ReservationComponent reservation) {
        super(reservation, "화이트보드", new BigDecimal("3000"));
    }
}
