package decorator;

import java.math.BigDecimal;

/**
 * 장비 데코레이터 추상 클래스
 */
public abstract class EquipmentDecorator implements ReservationComponent {
    protected ReservationComponent reservation;
    protected String equipmentName;
    protected BigDecimal equipmentPrice;

    public EquipmentDecorator(ReservationComponent reservation, String name, BigDecimal price) {
        this.reservation = reservation;
        this.equipmentName = name;
        this.equipmentPrice = price;
    }

    @Override
    public BigDecimal getPrice() {
        return reservation.getPrice().add(equipmentPrice);
    }

    @Override
    public String getDescription() {
        return reservation.getDescription() + " + " + equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public BigDecimal getEquipmentPrice() {
        return equipmentPrice;
    }
}