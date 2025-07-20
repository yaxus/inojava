package ru.yaxus.inojava.homeworks.HW04;

// import ru.yaxus.inojava.homeworks.HW04.TV;

public class App {
    public static void main(String[] args) {
        TV tv1 = new TV("Samsung", 27.3, "black");
        tv1.switchOn();
        tv1.switchChannel(6);
        String infoTv1 = tv1.getAllInfo();
        System.out.println(infoTv1);

        
        TV tv2 = TV.createTvFromCli();
        tv2.switchOn();
        tv2.switchChannel(1);
        String infoTv2 = tv2.getAllInfo();
        System.out.println(infoTv2);

        
    }
}
