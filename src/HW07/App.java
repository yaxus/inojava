package HW07;

import HW07.Discount.AbsoluteDiscount;
import HW07.Discount.DiscountInterface;
import HW07.Discount.PercentDiscount;
import HW07.Product.DiscountProduct;
import HW07.Product.Product;
import HW07.Product.ProductException;
import HW07.Product.ProductInterface;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class App {
    public static void main(String[] args) {

        var sc = new Scanner(System.in);

        // Павел Андреевич = 10000; Анна Петровна = 2000; Борис = 10
        Map<String,Person> personsMap = new HashMap<>();
        System.out.print("Введите список Покупателей в формате 'Покупатель1 = деньги; Покупатель2 = деньги; ...':");
        for (String[] strPersonsArr : readComplexString(sc)){
            String name = strPersonsArr[0];
            int cash = Integer.parseInt(strPersonsArr[1]);
            try{
                personsMap.put(name, new Person(name, cash));
            } catch(PersonException e){
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                return;
            }
        }

        // Хлеб = 40; Молоко = 60; Торт = 1000; Кофе растворимый = 879; Масло = 150
        Map<String, ProductInterface> productMap = new HashMap<>();
        System.out.print("Введите список Продуктов в формате 'Продукт1 = стоимость; Продукт2 = стоимость; ...':");
        for (String[] strProductArr : readComplexString(sc)){
            String name = strProductArr[0];
            int price = Integer.parseInt(strProductArr[1]);
            try{
                productMap.put(name, new Product(name, price));
            } catch (ProductException e){
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                return;
            }
        }

        // Хлеб = 10%-2day; Молоко = 10rub-1day; Торт = 110rub-10day; Кофе растворимый = 50%-1day
        System.out.print("Введите скидки Продуктов в формате 'Продукт1 = скидка%-1day; Продукт2 = скидкаРуб-2days; ...': ");
        Pattern patternDiscountPercent = Pattern.compile("(\\d{1,2})%-(\\d{1,3})days?");
        Pattern patternDiscountAbsolute = Pattern.compile("(\\d{1,2})rub-(\\d{1,3})days?");
        for (String[] strProductArr : readComplexString(sc)){
            String prodName = strProductArr[0];
            String strDiscount = strProductArr[1];
            Matcher matcherPrc = patternDiscountPercent.matcher(strDiscount);
            Matcher matcherAbs = patternDiscountAbsolute.matcher(strDiscount);
            boolean isFind = false;
            DiscountInterface discount = null;
            if (matcherPrc.find()) {
                double percent = Integer.parseInt(matcherPrc.group(1))/100.0;
                LocalDateTime endDate = LocalDateTime.now().plusDays(Integer.parseInt(matcherPrc.group(2)));
                discount = new PercentDiscount(percent, endDate);
                isFind = true;
            } else if (matcherAbs.find()){
                double absolute = Integer.parseInt(matcherAbs.group(1));
                LocalDateTime endDate = LocalDateTime.now().plusDays(Integer.parseInt(matcherAbs.group(2)));
                discount = new AbsoluteDiscount(absolute, endDate);
                isFind = true;
            }

            if (isFind){
                ProductInterface replaceProd = productMap.get(prodName);
                DiscountProduct prodDiscount = new DiscountProduct(replaceProd.getName(), replaceProd.getPrice());
                prodDiscount.setDiscount(discount);
                productMap.put(prodName, prodDiscount);
            }
        }


        // Павел Андреевич - Хлеб
        // Павел Андреевич - Масло
        // Анна Петровна - Кофе растворимый
        // Анна Петровна - Молоко
        // Анна Петровна - Молоко
        // Анна Петровна - Молоко
        // Анна Петровна - Торт
        // Борис - Торт
        // Павел Андреевич - Торт
        // END
        while (true){
            System.out.print("Введите строку в формате 'Покупатель1 - Продукт1' (END - завершение ввода):");
            String[] pair = readPairString(sc);
            if (pair[0].trim().equals("END")) break;
            String strPerson = pair[0];
            String strProduct = pair[1];

            try{
                personsMap.get(strPerson).addProduct(productMap.get(strProduct));
            } catch (PersonException e){
                System.out.println(e.getMessage());
            }
        }

        // Вывод результатов покупок
        for(Person person: personsMap.values()){
            System.out.println(person.getCurrentState());
        }

        sc.close();
    }

    public static String[][] readComplexString(Scanner sc){
        String str = sc.nextLine();
        String[] strArr = str.split("\\s*;\\s*");
        String[][] ret = new String[strArr.length][2];
        for(int i = 0; i < strArr.length; i++){
            ret[i] = strArr[i].split("\\s*=\\s*");
        }
        return ret;
    }

    public static String[] readPairString(Scanner sc){
        String str = sc.nextLine();
        return str.split("\\s*-\\s*");
    }

}
