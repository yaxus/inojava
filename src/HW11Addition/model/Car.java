package HW11Addition.model;

public class Car {
    private final String number;
    private final String model;
    private final String colour;
    private final Integer mileage;
    private final Integer cost;

    public Car(String number, String model, String colour, Integer mileage, Integer cost) {
        this.number = number;
        this.model = model;
        this.colour = colour;
        this.mileage = mileage;
        this.cost = cost;
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public String getColour() {
        return colour;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Integer getCost() {
        return cost;
    }

    public static String getHeader(){
        return "Number\tModel\tColor\tMileage\tCost";
    }

    public String getObjRow(){
        return number + "\t" + model + "\t" + colour + "\t" + mileage + "\t" + cost;
    }

    @Override
    public String toString() {
        return "Car{" +
                "number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", colour='" + colour + '\'' +
                ", mileage=" + mileage +
                ", cost=" + cost +
                '}';
    }
}
