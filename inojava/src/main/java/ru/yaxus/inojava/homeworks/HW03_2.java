package ru.yaxus.inojava.homeworks;

import java.util.Random;

public class HW03_2 {

    String[] map_descr = {"Камень", "Ножницы", "Бумага"};

    public HW03_2() {
        String res;
        int choice1 = getRandomChoice();
        int choice2 = getRandomChoice();
        if (choice1 == choice2) {
            res = "Ничья";
        }
        else if (choice1 == 0 && choice2 == 1 || 
                 choice1 == 1 && choice2 == 2 || 
                 choice1 == 2 && choice2 == 0){
            res = "Вася выиграл";
        }
        else {
            res = "Вася програл";
        }

        System.out.println(String.format("Вася: %s, Петя: %s\nРезультат: %s"
                                        , choice1 + " (" + map_descr[choice1] + ")"
                                        , choice2 + " (" + map_descr[choice2] + ")"
                                        , res));
    }

    int getRandomChoice() {
        Random rnd = new Random();
        return rnd.nextInt(3);
    }
    
}
