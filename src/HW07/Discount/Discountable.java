package HW07.Discount;

public interface Discountable {
    void setDiscount(DiscountInterface discount);
    DiscountInterface getDiscount();
    double getDiscountPrice();
}
