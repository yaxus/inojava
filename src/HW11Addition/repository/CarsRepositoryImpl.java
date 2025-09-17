package HW11Addition.repository;

import HW11Addition.model.Car;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

public class CarsRepositoryImpl implements CarsRepository, Iterable<Car>{

    private final String fileDB = "src/HW11Addition/data/cars.txt";
    private HashMap<String, Car> cars = new HashMap<>();

    @Override
    public void loadFromFile() {

        try(BufferedReader reader = new BufferedReader(new FileReader(this.fileDB))){
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] spl = line.split("\\|");
                this.addCar(new Car(spl[0], spl[1], spl[2], Integer.valueOf(spl[3]), Integer.valueOf(spl[4])));
            }

        } catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public Stream<Car> getStream() {
        return cars.values().stream();
    }

    @Override
    public Car getCarByNumber(String number) {
        return cars.get(number);
    }

    @Override
    public void addCar(Car c) {
        this.cars.put(c.getNumber(), c);
    }

    @Override
    public void deleteCar(String number) {
        this.cars.remove(number);
    }

    @Override
    public Iterator<Car> iterator() {
        return this.cars.values().iterator();
    }
}
