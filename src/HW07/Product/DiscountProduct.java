package HW07.Product;

import HW07.Discount.DiscountInterface;
import HW07.Discount.Discountable;

public class DiscountProduct extends BaseProduct implements Discountable {

    protected DiscountInterface discount;

    public DiscountProduct(String name, double price) throws ProductException {
        super(name, price);
    }

    @Override
    public void setDiscount(DiscountInterface discount) {
        this.discount = discount;
    }

    @Override
    public DiscountInterface getDiscount() {
        return discount;
    }

    @Override
    public double getDiscountPrice() {
        return discount.calcDiscountPrice(super.price);
    }

    @Override
    public double getPrice() {
        return getDiscountPrice();
    }

    @Override
    public String toString() {
        return "DiscountProduct{" +
                "discount=" + discount.getDiscountSetting() +
                ", name='" + name + '\'' +
                ", price with discount=" + getDiscountPrice() + ", original price=" + price +
                '}';
    }


}
