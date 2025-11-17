package strategy;
import service.Registering;

import java.math.BigDecimal;

// Strategy Pattern (전략)
// 
public interface PricingStrategy {
    BigDecimal calculatePrice(Registering registering);
}