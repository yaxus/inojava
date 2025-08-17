package HW07.Discount;

import java.time.LocalDateTime;

/**
 * Абсолютная скидка, например 100 руб.
 */
public class AbsoluteDiscount extends DiscountAbstract {

    private final double absolute;

    public AbsoluteDiscount(double absolute, LocalDateTime startDate, LocalDateTime endDate) {
        if (absolute <= 0){
            this.absolute = 0;
            return;
        }
        super._setBeginTime(startDate);
        super._setEndTime(endDate);
        this.absolute = absolute;
    }


    public AbsoluteDiscount(double absolute, LocalDateTime endDate) {
        this(absolute, LocalDateTime.now(), endDate);
    }


    @Override
    public String getDiscountSetting() {
        return "%s руб. до %s".formatted(absolute, super.getEndDate());
    }

    @Override
    public double calcDiscount(double price) {
        return absolute;
    }



}
