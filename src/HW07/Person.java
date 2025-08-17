package HW07;

import HW07.Product.ProductInterface;

import java.util.ArrayList;
import java.util.Objects;

public class Person {

    private String name;
    private double cash;
    private final ArrayList<ProductInterface> cart = new ArrayList<>();

    public Person(String name, int cache) throws PersonException {
        this.setName(name);
        this.setCash(cache);
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.length() < 3){
            throw new IllegalArgumentException("Имя не может быть короче 3 символов, попытка установить: " + name.length());
        }
        this.name = name;
    }

    public void setCash(int cash) throws IllegalArgumentException {
        if (cash < 0){
            throw new IllegalArgumentException("Деньги не могут быть отрицательнымы, попытка установить: " + cash);
        }
        this.cash = cash;
    }

    public void addProduct(ProductInterface product) throws PersonException {
        if (this.cash - product.getPrice() < 0){
            String msg = String.format("%s не может позволить себе %s"
                    , this.name
                    , product.getName());
            throw new PersonException(msg);
        }
        this.cash-=product.getPrice();
        this.cart.add(product);
    }

    public String getCurrentState(){
        int cntProducts = this.cart.size();
        if (cntProducts == 0)
            return this.name + " (остаток: " + cash + ") - Ничего не куплено";
        String[] prodNames = new String[cntProducts];
        int i = 0;
        for (ProductInterface product : this.cart){
//            prodNames[i++] = product.getName();
            prodNames[i++] = product.toString();
        }
        return this.name + " (остаток: " + cash + ")\n - " + String.join(", \n - ", prodNames);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", cash=" + cash +
                ", cart=" + cart +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return cash == person.cash && Objects.equals(name, person.name) && Objects.equals(cart, person.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cash, cart);
    }
}

class PersonException extends Exception{
    public PersonException(String message) {
        super(message);
    }
}
