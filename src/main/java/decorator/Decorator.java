package decorator;
import factory.Toy;
public abstract class Decorator implements Toy{
    protected Toy toy;
    public Decorator(Toy toy){
        this.toy=toy;
    }
    @Override
    public String getName(){
        return toy.getName();
    }
    @Override
    public double getPrice(){
        return toy.getPrice();
    }
    @Override
    public void displayToyInfo(){
        toy.displayToyInfo();
    }
}
