package factory;

public class ToyFactory {

    public Toy createToy(String type, String name, double price) {
        switch (type) {
            case "Doll":
                return new Doll(name, price);
            case "Car":
                return new Car(name, price);
            case "Robot":
                return new Robot(name, price);
            default:
                throw new IllegalArgumentException("Неизвестный тип игрушки: " + type);
        }
    }
}
