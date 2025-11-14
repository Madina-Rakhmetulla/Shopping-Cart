package adapter;

public class KaspiPayment {
    public boolean payByPhone(String phoneNumber, double amount) {
        System.out.println("Оплата через Kaspi на номер " + phoneNumber + " суммы " + amount + "₸.");
        return true;

    }
}
