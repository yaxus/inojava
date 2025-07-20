package ru.yaxus.inojava.homeworks.HW04;

import java.util.Scanner;

public class TV {

    String brand;
    double diagonal;
    String colour;

    boolean power;
    int channel;

    public TV(String brand, double diagonal, String colour){
        this.brand = brand;
        this.diagonal = diagonal;
        this.colour = colour;

        // return this;
    }

    public void switchOn(){
        this.power = true;
    }

    public void switchOff(){
        this.power = false;
    }

    public void switchChannel(int intChannel){
        this.channel = intChannel;
    }

    public String getAllInfo(){
        String ret = 
            "Бренд: %s\n" +
            "Диагональ: %s\"\n" +
            "Цвет: %s\n" +
            "Питание: %s\n" +
            "Текущий канал: %s\n";
        return String.format(ret, this.brand, this.diagonal, this.colour
                                , this.power, this.channel);
    }

    public int getCurrentChannel(){
        return this.channel;
    }

    public static TV createTvFromCli(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите бренд телевизора:");
        String cli_brand = scanner.nextLine();
        System.out.println("Введите диагональ телевизора:");
        double cli_diagonal = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите цвет телевизора:");
        String cli_colour = scanner.nextLine();
        scanner.close();
        return new TV(cli_brand, cli_diagonal, cli_colour);
    }

}
