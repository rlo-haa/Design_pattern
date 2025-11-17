package studyroom.decorator;

import java.math.BigDecimal;

class WebcamDecorator extends EquipmentDecorator {
    public WebcamDecorator(ReservationComponent reservation) {
        super(reservation, "웹캠", new BigDecimal("5000"));
    }
}