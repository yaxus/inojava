package ru.yaxus.inojava.homeworks;

import java.util.Scanner;

public class HW03_1 {

    String username;

    public HW03_1() {
        this.getNameFromCli();
        this.outHi();
    }

    void getNameFromCli() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя:");
        this.username = scanner.nextLine();
        scanner.close();
    }

    void outHi() {
        System.out.println(String.format("Привет, %s!", this.username));
    }
    
}
