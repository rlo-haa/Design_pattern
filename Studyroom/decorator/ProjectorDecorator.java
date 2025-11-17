package decorator;

import java.math.BigDecimal;

public class ProjectorDecorator extends EquipmentDecorator {
    private static final BigDecimal PRICE = new BigDecimal("10000");

    public ProjectorDecorator(ReservationComponent reservation) {
        super(reservation, "프로젝터", PRICE);
    }
}