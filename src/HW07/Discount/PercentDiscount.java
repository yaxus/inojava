package HW07.Discount;

import java.time.LocalDateTime;

import static java.lang.Math.round;

/**
 * Процентная скидка, например 10%.
 * Задается в диапазоне от 0 до 1 не включая границы
 */
public class PercentDiscount extends DiscountAbstract {

    private final double percent;

    public PercentDiscount(double percent, LocalDateTime startDate, LocalDateTime endDate) {
        if (percent <= 0 || percent >= 1){
            this.percent = 0;
            return;
        }
        super._setBeginTime(startDate);
        super._setEndTime(endDate);
        this.percent = percent;
    }

    public PercentDiscount(double percent, LocalDateTime endDate) {
        this(percent, LocalDateTime.now(), endDate);
    }

    @Override
    public String getDiscountSetting() {
        return "%s%% до %s".formatted(percent*100, super.getEndDate());
    }

    @Override
    public double calcDiscount(double price) {
        return round(price * this.percent * 100.0) / 100.0;
    }
}
