package strategy;

public class Holiday implements Discount{
    @Override
    public double discount(double price){
        return price*0.8;
    }
    @Override
    public String name(){
        return "Праздничная скидка 20%";
    }
}
