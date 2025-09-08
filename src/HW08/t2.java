package HW08;

import java.util.*;

/**
 * Задание 2:
 * С консоли на вход подается две строки s и t. Необходимо вывести true, если одна
 * строка является валидной анаграммой другой строки, и false – если это не так.
 * Анаграмма – это слово, или фраза, образованная путем перестановки букв другого
 * слова или фразы, обычно с использованием всех исходных букв ровно один раз.
 * Для проверки:
 * ● Бейсбол – бобслей
 * ● Героин – регион
 * ● Клоака – околка
 */
public class t2 {
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            System.out.print("Введите строку 1: ");
            String s = sc.nextLine();
            System.out.print("Введите строку 2: ");
            String t = sc.nextLine();
            System.out.print("Строки являются анаграммой?: ");
            System.out.println(isAnagram(s, t));
        }
    }

    public static boolean isAnagram(String s1, String s2){
        Set<Character> set1 = new HashSet<>(s1.trim().toLowerCase().chars().mapToObj(c -> (char) c).toList());
        Set<Character> set2 = new HashSet<>(s2.trim().toLowerCase().chars().mapToObj(c -> (char) c).toList());
        return set1.equals(set2);
    }
}
