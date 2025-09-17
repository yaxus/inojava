package HW11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {

        ArrayList<Car> cars = getCarsFromDB("src/HW11/car_base.txt");
        System.out.println("Автомобили в базе:");
        System.out.println(Car.getHeader());
        for (Car car : cars){
            System.out.println(car.getObjRow());
        }
        System.out.println();

        String colorToFind = "Black";
        Integer mileageToFind = 0;
        String res1 = cars.stream()
                .filter(car -> car.getColour().equalsIgnoreCase(colorToFind) || car.getMileage().equals(mileageToFind))
                .map(Car::getNumber)
                .collect(Collectors.joining(" "));
        System.out.println("Номера автомобилей по цвету или пробегу: " + res1);

        Integer costToFindMin = 700_000;
        Integer costToFindMax = 800_000;
        Long res2 = cars.stream()
                .filter(car -> car.getCost() >= costToFindMin && car.getCost() >= costToFindMax)
                .distinct()
                .count();
        System.out.printf("Уникальные автомобили: %s шт.%n",  res2);

        String res3 = cars.stream()
                .sorted(Comparator.comparingInt(Car::getCost))
                .map(Car::getColour)
                .findFirst()
                .get();
        System.out.printf("Цвет автомобиля с минимальной стоимостью: %s%n",  res3);


        BiFunction<Stream<Car>, String, Double> func4 = (Stream<Car> s, String strModel) -> s
                .filter(car -> car.getModel().equalsIgnoreCase(strModel))
                .mapToInt(Car::getCost)
                .average()
                .orElse(0.0);

        String modelToFind1 = "Toyota";
        Double res4_1 = func4.apply(cars.stream(), modelToFind1);
        System.out.printf("Средняя стоимость модели %s: %s%n", modelToFind1, res4_1);
        String modelToFind2 = "Volvo";
        Double res4_2 = func4.apply(cars.stream(), modelToFind2);
        System.out.printf("Средняя стоимость модели %s: %s%n", modelToFind2, res4_2);
    }

    public static ArrayList<Car> getCarsFromDB(String file) {

        ArrayList<Car> ret = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] spl = line.split("\\|");
                ret.add(new Car(spl[0], spl[1], spl[2], Integer.valueOf(spl[3]), Integer.valueOf(spl[4])));
            }

        } catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }

        return ret;
    }
}
