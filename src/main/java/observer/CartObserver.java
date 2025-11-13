package observer;

public class CartObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("[Корзина]" + message);
    }
}
