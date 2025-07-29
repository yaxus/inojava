package ru.yaxus.inojava.homeworks.HW05;

/*
* Задача 2.
* Задана последовательность, состоящая только из символов ‘>’, ‘<’ и ‘-‘.
* Требуется найти количество стрел, которые спрятаны в этой последовательности.
* Стрелы – это подстроки вида ‘>>-->’ и ‘<--<<’.
* Входные данные: в первой строке входного потока записана строка, состоящая из символов ‘>’, ‘<’ и ‘-‘
* (без пробелов). Строка может содержать до 106 символов.
* Выходные данные: в единственную строку выходного потока нужно вывести искомое количество стрелок.
* */

import java.util.Scanner;
import java.util.regex.*;

public class HW05_2 {

    static final int MAX_LEN = 106;
    static final String FIND_PATTERN = "(?=((>>-->)|(<--<<)))";

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Введите строку из символов: ‘>’, ‘<’ и ‘-‘");
        String s = scanner.next();
        scanner.close();

        // Обрезать строку по условию
        String subst = (s.length() > MAX_LEN) ? s.substring(0, MAX_LEN) : s;

        // Поиск через регулярные выражения
        Pattern pattern = Pattern.compile(FIND_PATTERN);
        Matcher matcher = pattern.matcher(subst);
        long count = matcher.results().count();

        System.out.println("Всего стрелок с учетом возможного пересечения: "+count);
    }
}
