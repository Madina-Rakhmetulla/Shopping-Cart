package strategy;

public class Sale implements Discount{
    @Override
    public double discount(double price){
        return price*0.5;
    }
    @Override
    public String name(){
        return "Распродажа 50%";
    }
}
