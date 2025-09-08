package HW08;

import java.util.*;

/**
 * Задание 3:
 * Реализовать класс PowerfulSet, в котором должны быть следующие методы:
 * ● public <T> Set<T> intersection(Set<T> set1, Set<T> set2) – возвращает
 * пересечение двух наборов.
 * Пример: set1 = {1, 2, 3}, set2 = {0, 1, 2, 4}. Вернуть {1, 2}
 * ● public <T> Set<T> union(Set<T> set1, Set<T> set2) – возвращает
 * объединение двух наборов
 * Пример: set1 = {1, 2, 3}, set2 = {0, 1, 2, 4}. Вернуть {0, 1, 2, 3, 4}
 * ● public <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2) –
 * возвращает элементы первого набора без тех, которые находятся также и
 * во втором наборе.
 * Пример: set1 = {1, 2, 3}, set2 = {0, 1, 2, 4}. Вернуть {3}
 */
public class t3 {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(0, 1, 2, 4));
        System.out.println(PowerfulSet.intersection(set1, set2));
        System.out.println(PowerfulSet.union(set1, set2));
        System.out.println(PowerfulSet.relativeComplement(set1, set2));

        Set<String> setA = new HashSet<>(Arrays.asList("a", "b", "c"));
        Set<String> setB = new HashSet<>(Arrays.asList("c", "b", "d"));
        System.out.println(PowerfulSet.intersection(setA, setB));
        System.out.println(PowerfulSet.union(setA, setB));
        System.out.println(PowerfulSet.relativeComplement(setA, setB));
    }
}

class PowerfulSet{
    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2){
        Set<T> ret = new HashSet<>(Set.copyOf(set1));
        ret.retainAll(set2);
        return ret;
    }
    public static <T> Set<T> union(Set<T> set1, Set<T> set2){
        Set<T> ret = new HashSet<>(Set.copyOf(set1));
        ret.addAll(set2);
        return ret;
    }
    public static <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2){
        Set<T> ret = new HashSet<>(Set.copyOf(set1));
        ret.removeAll(set2);
        return ret;
    }
}
