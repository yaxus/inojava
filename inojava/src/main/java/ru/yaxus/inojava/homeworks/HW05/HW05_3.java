package ru.yaxus.inojava.homeworks.HW05;

/*
* Задача 3*.
* Задана строка, состоящая из букв английского алфавита, разделенных одним пробелом.
* Необходимо каждую последовательность символов упорядочить по возрастанию и вывести слова в нижнем регистре.
* Входные данные: в единственной строке последовательность символов представляющее два слова.
* Выходные данные: упорядоченные по возрастанию буквы в нижнем регистре.
* */

import java.util.Arrays;
import java.util.Scanner;

public class HW05_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите два слова через пробел:");
        String row = scanner.nextLine();
        scanner.close();

        String[] words = row.split(" ");
        if(words.length != 2){
            System.out.println("Ошибка, требуется ввести ровно два слова");
            scanner.close();
            return;
        }
        String[] resultArr = new String[words.length];
        for (int i = 0; i < words.length; i++){
            resultArr[i] = wordHandler(words[i]);
            System.out.println("Слово до обработки: " + words[i] + ", после: " + resultArr[i]);
        }
        String result = String.join(" ", resultArr);
        System.out.println("Результат: " + result);
    }

    static String wordHandler(String word){
        String lowerRow = word.toLowerCase();
        char[] chars = lowerRow.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }
}
