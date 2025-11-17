package studyroom.decorator;

import java.math.BigDecimal;

class ProjectorDecorator extends EquipmentDecorator {
    public ProjectorDecorator(ReservationComponent reservation) {
        super(reservation, "프로젝터", new BigDecimal("10000"));
    }
}
