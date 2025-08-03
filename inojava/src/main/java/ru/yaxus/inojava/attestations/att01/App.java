package ru.yaxus.inojava.attestations.att01;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        var sc = new Scanner(System.in);

        // Павел Андреевич = 10000; Анна Петровна = 2000; Борис = 10
        Map<String,Person> personsMap = new HashMap<>();
        System.out.println("Введите список Покупателей в формате 'Покупатель1 = деньги; Покупатель2 = деньги'");
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
        Map<String,Product> productMap = new HashMap<>();
        System.out.println("Введите список Продуктов в формате 'Продукт1 = стоимость; Продукт2 = стоимость'");
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
            System.out.println("Введите строку в формате 'Пользователь1 - Продукт1");
            System.out.println("END - завершение ввода");
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
