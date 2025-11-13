package observer;
import java.util.ArrayList;
import java.util.List;

public class ToyNotifier implements Subject {
    private List<Observer> observers=new ArrayList<>();

    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(String message){
        for(Observer observer:observers){
            observer.update(message);
        }
    }


    public void notifyPriceChanged(String toyName,double newPrice){
        notifyAllObservers("Цена игрушки \"" +toyName + "\" изменена на " + newPrice +"₸.");
    }

    public void notifyStockChanged(String toyName, int newStock){
        notifyAllObservers( "Остаток игрушки \"" +toyName + "\" теперь: " + newStock + "шт.");
    }

    public void notifyToyAdded(String toyName){
        notifyAllObservers("Добавлена новая игрушка: " + toyName +"!");
    }

    public void notifyToyRemoved(String toyName){
        notifyAllObservers("Игрушка \"" + toyName + "\" снята с продажи.");
    }
}
