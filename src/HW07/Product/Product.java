package HW07.Product;

/*
* Product сохранен для обратной совместимости
* */
public class Product extends BaseProduct {

    public Product(String name, int price) throws ProductException {
        super(name, price);
    }

}
