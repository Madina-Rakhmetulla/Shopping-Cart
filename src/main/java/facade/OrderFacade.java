package facade;

import factory.Toy;
import observer.ToyNotifier;
import strategy.Discount;
import adapter.PaymentSystem;
import java.util.List;

public class OrderFacade {
    private ToyNotifier notifier;
    public OrderFacade(ToyNotifier notifier) {
        this.notifier=notifier;
    }

    public String checkout(List<Toy> cart, Discount discount, PaymentSystem paymentMethod,String customerData) {
        if (cart==null || cart.isEmpty()){
            return "Корзина пуста!";
        }

        double totalPrice=0;
        for (Toy toy : cart) {
            totalPrice+=toy.getPrice();
        }
         double finalPrice=discount.discount(totalPrice);


        boolean paymentSuccess=paymentMethod.pay(finalPrice,customerData);
        if (!paymentSuccess){
            return "Ошибка оплаты!";
        }

        for (Toy toy : cart) {
            notifier.notifyAllObservers("Остаток товара \"" + toy.getName() + "\" уменьшился после покупки.");
        }

        for (Toy toy : cart) {
            notifier.notifyAllObservers("Товар \"" + toy.getName() + "\" куплен! Применена скидка: " +discount.name());
        }

        cart.clear();

        return "Покупка успешно оформлена! Итоговая сумма: " + finalPrice + "₸.";
    }
}
