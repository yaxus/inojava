package ru.yaxus.inojava.attestations.att01;

import java.util.Objects;

public class Product {
    private String name;
    private int price;

    public Product(String name, int price) throws ProductException {
        this.setName(name);
        this.setPrice(price);
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.isEmpty()){
            throw new IllegalArgumentException("Название продукта не может быть пустой строкой");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPrice(int price) throws IllegalArgumentException {
        if (price < 0){
            throw new IllegalArgumentException("Стоимость продукта не может быть отрицательной, попытка установить: " + price);
        }
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "Product{" +
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

class ProductException extends Exception{
    public ProductException(String message) {
        super(message);
    }
}
