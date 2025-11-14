package adapter;

public class KaspiAdapter implements PaymentSystem {
    private KaspiPayment kaspiPayment;

    public KaspiAdapter(KaspiPayment kaspiPayment) {
        this.kaspiPayment = kaspiPayment;
    }

    @Override
    public boolean pay(double amount, String customerData) {

        return kaspiPayment.payByPhone(customerData, amount);
    }
}
