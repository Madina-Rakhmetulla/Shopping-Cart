package adapter;

public class PayPalAdapter implements PaymentSystem {
    private PayPalPayment payPalPayment;

    public PayPalAdapter(PayPalPayment payPalPayment) {
        this.payPalPayment = payPalPayment;
    }

    @Override
    public boolean pay(double amount, String customerData) {

        return payPalPayment.sendPayPal(customerData, amount);
    }
}
