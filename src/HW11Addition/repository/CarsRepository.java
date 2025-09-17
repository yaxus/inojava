package HW11Addition.repository;

import HW11Addition.model.Car;

import java.util.stream.Stream;

public interface CarsRepository {
    public void loadFromFile();
    public Stream<Car> getStream();
    public Car getCarByNumber (String number);
    public void addCar(Car c);
    public void deleteCar(String number);
}
