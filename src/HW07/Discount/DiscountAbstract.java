package HW07.Discount;

import java.time.LocalDateTime;

public abstract class DiscountAbstract implements DiscountInterface {
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    {
        beginDate = LocalDateTime.now();
    }

    protected void _setBeginTime(LocalDateTime beginDate){
        this.beginDate = beginDate;
    }

    protected void _setEndTime(LocalDateTime endDate){
        this.endDate = endDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }

    protected boolean isTimeChecked(){
        LocalDateTime currentLDT = LocalDateTime.now();
        return !currentLDT.isBefore(beginDate) && currentLDT.isBefore(endDate);
    }

    public double calcDiscountPrice(double price) {
        double calcDiscount = calcDiscount(price);
        if (isTimeChecked() && calcDiscount < price){
            return price - calcDiscount;
        }
        return price;
    }


}
