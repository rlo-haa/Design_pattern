package studyroom.decorator;

import java.math.BigDecimal;

// --- 1. Component 인터페이스 ---
interface ReservationComponent {
    BigDecimal getPrice();
    String getDescription();
} 