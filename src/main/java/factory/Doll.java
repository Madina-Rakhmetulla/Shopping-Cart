package factory;

public class Doll implements Toy {
    private String name;
    private double price;

    public Doll(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void displayToyInfo() {
        System.out.println("Название игрушки: " + getName() + ", Цена: " + getPrice() + "₸, Тип: Кукла");
    }
}
