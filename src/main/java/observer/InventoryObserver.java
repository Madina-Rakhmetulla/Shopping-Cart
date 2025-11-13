package observer;

public class InventoryObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("[Склад]" + message);
    }
}
