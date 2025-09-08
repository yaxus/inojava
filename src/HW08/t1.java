package HW08;

import java.util.*;

/**
 * Задание 1:
 * Реализовать метод, который на вход принимает ArrayList<T>, а возвращает набор
 * уникальных элементов этого массива. Решить, используя коллекции
 */
public class t1 {
    public static void main(String[] args) {
        // T String
        ArrayList<String> str = new ArrayList<>(Arrays.asList("s1", "s2", "s3", "s1"));
        System.out.println(uniqueList(str));

        // T Integer
        ArrayList<Integer> i = new ArrayList<>(Arrays.asList(4, 4, 5, 6));
        System.out.println(uniqueList(i));
    }

    public static <T> LinkedHashSet<T> uniqueList(ArrayList<T> arr){
        return new LinkedHashSet<>(arr);
    }
}
