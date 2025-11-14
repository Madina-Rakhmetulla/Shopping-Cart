package decorator;
import factory.Toy;
public class Warranty extends Decorator{
    public  Warranty(Toy toy){
        super(toy);
    }
    @Override
    public double getPrice(){
        return toy.getPrice()+1000;
    }
    @Override
    public void displayToyInfo(){
        System.out.println("+ Гарантия год ");
        toy.displayToyInfo();
    }
}

