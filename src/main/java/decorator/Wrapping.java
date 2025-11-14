package decorator;
import factory.Toy;
public class Wrapping extends Decorator{
    public  Wrapping(Toy toy){
        super(toy);
    }
    @Override
    public double getPrice(){
        return toy.getPrice()+500;
    }
    @Override
    public void displayToyInfo(){
        System.out.println("+ Подарочная упаковка ");
        toy.displayToyInfo();
    }
}

