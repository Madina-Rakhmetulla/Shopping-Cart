package strategy;

public class Nodiscount implements Discount{
    @Override
    public double discount(double price){
        return price;
    }
    @Override
    public String name(){
        return "Без скидки";
    }
}
