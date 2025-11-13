package decorator;
import factory.Toy;
public class Delivery extends Decorator{
    public  Delivery(Toy toy){
        super(toy);
    }
    @Override
    public double getPrice(){
        return toy.getPrice()+1500;
    }
    @Override
    public void displayToyInfo(){
        System.out.println("+ Быстрая доставка ");
        toy.displayToyInfo();
    }
}

