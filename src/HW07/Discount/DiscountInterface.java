package HW07.Discount;

public interface DiscountInterface {

    String getDiscountSetting();
    double calcDiscount(double price);
    double calcDiscountPrice(double price);
}
