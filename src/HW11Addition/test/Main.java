package HW11Addition.test;

import HW11Addition.model.Car;
import HW11Addition.repository.CarsRepositoryImpl;

import java.io.*;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        String fileOutput = "src/HW11Addition/test/output.txt";

        CarsRepositoryImpl carsRepo = new CarsRepositoryImpl();
        carsRepo.loadFromFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput))) {

            writer.write("Автомобили в базе:\n");
            writer.write(Car.getHeader()+"\n");
            for (Car car : carsRepo){
                writer.write(car.getObjRow()+"\n");
            }
            writer.write("\n");

            String colorToFind = "Black";
            Integer mileageToFind = 0;
            String res1 = carsRepo.getStream()
                    .filter(car -> car.getColour().equalsIgnoreCase(colorToFind) || car.getMileage().equals(mileageToFind))
                    .map(Car::getNumber)
                    .collect(Collectors.joining(" "));
            writer.write(String.format("Номера автомобилей по цвету или пробегу: " + res1));

            Integer costToFindMin = 700_000;
            Integer costToFindMax = 800_000;
            Long res2 = carsRepo.getStream()
                    .filter(car -> car.getCost() >= costToFindMin && car.getCost() >= costToFindMax)
                    .distinct()
                    .count();
            writer.write(String.format("Уникальные автомобили: %s шт.%n",  res2));

            String res3 = carsRepo.getStream()
                    .sorted(Comparator.comparingInt(Car::getCost))
                    .map(Car::getColour)
                    .findFirst()
                    .get();
            writer.write(String.format("Цвет автомобиля с минимальной стоимостью: %s%n",  res3));


            BiFunction<Stream<Car>, String, Double> func4 = (Stream<Car> s, String strModel) -> s
                    .filter(car -> car.getModel().equalsIgnoreCase(strModel))
                    .mapToInt(Car::getCost)
                    .average()
                        .orElse(0.0);

            String modelToFind1 = "Toyota";
            Double res4_1 = func4.apply(carsRepo.getStream(), modelToFind1);
            writer.write(String.format("Средняя стоимость модели %s: %s%n", modelToFind1, res4_1));
            String modelToFind2 = "Volvo";
            Double res4_2 = func4.apply(carsRepo.getStream(), modelToFind2);
            writer.write(String.format("Средняя стоимость модели %s: %s%n", modelToFind2, res4_2));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
