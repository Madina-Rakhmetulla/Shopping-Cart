package adapter;

public class PayPalPayment {
    public boolean sendPayPal(String email, double amount) {
        System.out.println("Оплата через PayPal на email " + email + " суммы " + amount + "₸.");
        return true;
    }
}
