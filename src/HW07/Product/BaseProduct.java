package HW07.Product;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseProduct implements ProductInterface {

    protected String name;
    protected double price;

    BaseProduct(String name, double price){
        this._setName(name);
        this._setPrice(price);
    }

    protected void _setName(String name) throws IllegalArgumentException {
        Pattern onlyDigitsPattern = Pattern.compile("^\\d+$");
        Matcher matcher = onlyDigitsPattern.matcher(name);
        if (name.length() < 3){
            throw new IllegalArgumentException("Название продукта не может быть короче 3 символов, попытка установить: " + name.length());
        } else if (matcher.matches()) {
            throw new IllegalArgumentException("Название продукта не может состоять из одних цифр, попытка установить: " + name);
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    protected void _setPrice(double price) throws IllegalArgumentException {
        if (price <= 0){
            throw new IllegalArgumentException("Стоимость продукта не может быть отрицательной или равна 0, попытка установить: " + price);
        }
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }


    @Override
    public String toString() {
        return "BaseProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

}
