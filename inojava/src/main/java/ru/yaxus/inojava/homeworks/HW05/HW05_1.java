package ru.yaxus.inojava.homeworks.HW05;

/*
* Задача 1.
* Для введенной с клавиатуры буквы английского алфавита нужно вывести слева стоящую
* букву на стандартной клавиатуре. При этом клавиатура замкнута, т.е. справа от буквы «p»
* стоит буква «a», а слева от "а" буква "р", также соседними считаются буквы «l» и буква «z»,
* а буква «m» с буквой «q».
* Входные данные: строка входного потока содержит один символ —
* маленькую букву английского алфавита.
* Выходные данные: следует вывести букву стоящую слева от заданной
* буквы, с учетом замкнутости клавиатуры.
* */

import java.util.Scanner;

public class HW05_1 {

    static final String keyboardChars = "qwertyuiopasdfghjklzxcvbnm";

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Введите маленькую букву английского алфавита");
        String input=scanner.next();
        scanner.close();
        char letter= input.charAt(0);
        int index = keyboardChars.indexOf(letter);

        if(index == -1){
            System.out.println("Неподдерживамый символ");
        } else {
            int leftIndex=(index+keyboardChars.length()-1) % keyboardChars.length();
            System.out.println("Индекс левой буквы: "+leftIndex);
            char leftNeighbor=keyboardChars.charAt(leftIndex);
            System.out.println("Буква слева на клавиатуре: "+ leftNeighbor);
        }
    }
}
