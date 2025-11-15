import facade.OrderFacade;
import factory.*;
import observer.*;
import strategy.*;
import decorator.*;
import adapter.*;
import factory.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
public class Main {
    public static void main(String[] args){
        Scanner scanner =new Scanner(System.in);
        ToyFactory factory =new ToyFactory();
        ToyNotifier notifier =new ToyNotifier();
        notifier.addObserver(new CartObserver());
        notifier.addObserver(new InventoryObserver());
        OrderFacade orderFacade =new OrderFacade(notifier);

        Map<String, Integer> stock =new HashMap<>();
        stock.put("Doll",5);
        stock.put("Car",4);
        stock.put("Robot",3);
        Map<String, Double> prices =new HashMap<>();
        prices.put("Doll",3500.0);
        prices.put("Car",5000.0);
        prices.put("Robot",8000.0);
        Map<String, String> displayNames =new HashMap<>();
        displayNames.put("Doll","Кукла");
        displayNames.put("Car","Машинка");
        displayNames.put("Robot","Робот");

        System.out.println("Магазин игрушек\n");
        System.out.println("Введите дату покупки (гггг-мм-дд)");
        String dateInput =scanner.nextLine();
        LocalDate date =LocalDate.parse(dateInput);
        Discount discount = chooseDiscountByDate(date);
        System.out.println("Ваша скидка:" +discount.name());

        List<Toy> cart =new ArrayList<>();

        boolean running =true;
        while (running){
            System.out.println();
            System.out.println("1 Показать католог игрушек");
            System.out.println("2 Добавить игрушку в корзину");
            System.out.println("3 Посмотреть корзину");
            System.out.println("4 Добавить услуги к товару");
            System.out.println("5 Информация о скидках");
            System.out.println("6 Оформить заказ");
            System.out.println("7 Выйти");
            System.out.print(">");

            int choice;
            try {
                choice =Integer.parseInt(scanner.nextLine());
            }
            catch (Exception e){
                System.out.println("Некорректный ввод");
                continue;
            }
            switch (choice){
                case 1 ->showCatalog(stock,prices,displayNames);
                case 2 ->addToyCart(scanner,factory,stock,prices,displayNames,notifier,cart);
                case 3 ->showCart(cart);
                case 4 ->addServicesToItem(scanner, cart);
                case 5 ->showDiscountInfo();
                case 6 ->{
                    boolean done =checkout(scanner,orderFacade,cart,discount);
                    if (done) running =false;
                }
                case 7 ->{
                    System.out.println("Вы вышли из магазина");
                    running =false;
                }
                default -> System.out.println("Такого пунката нет");
            }
        }
    }

    private static void showCatalog(Map<String, Integer> stock,
                                    Map<String, Double> prices,
                                    Map<String, String> displayNames){
        System.out.println("Католог игрушек");
        System.out.println("1)" + displayNames.get("Doll") + "-" + prices.get("Doll") +"₸(на складе:" + stock.get("Doll")+ ")");
        System.out.println("2)" + displayNames.get("Car") + "-" + prices.get("Car") +"₸(на складе:" + stock.get("Car")+ ")");
        System.out.println("3)" + displayNames.get("Robot") + "-" + prices.get("Robot") +"₸(на складе:" + stock.get("Robot")+ ")");
    }
    private static void addToyCart(Scanner scanner,
                                   ToyFactory factory,
                                   Map<String, Integer> stock,
                                   Map<String, Double> prices,
                                   Map<String, String> displayNames,
                                   ToyNotifier notifier,
                                   List<Toy> cart){
        System.out.println("\nКакую игрушку добавить?");
        System.out.println("1-" +displayNames.get("Doll"));
        System.out.println("2-" +displayNames.get("Car"));
        System.out.println("3-" +displayNames.get("Robot"));
        System.out.println(">");
        int t;
        try {
            t =Integer.parseInt(scanner.nextLine());
        } catch (Exception e){
            System.out.println("Некорректный ввод");
            return;
        }
        String type =switch (t){
            case 1 ->"Doll";
            case 2 ->"Car";
            case 3 ->"Robot";
            default ->null;
        };
        if (type == null) {
            System.out.println("Нет такого товара.");
            return;
        }
        int currentStock = stock.get(type);
        if (currentStock <= 0) {
            System.out.println("Товар закончился.");
            notifier.notifyToyRemoved(displayNames.get(type));
            return;
        }
        Toy toy = factory.createToy(type, displayNames.get(type), prices.get(type));
        cart.add(toy);
        stock.put(type, currentStock - 1);
        notifier.notifyToyAdded(toy.getName());
        notifier.notifyStockChanged(toy.getName(), stock.get(type));

        System.out.println("Добавлено: " + toy.getName() + " (" + toy.getPrice() + "₸)");
    }
        private static void showCart(List<Toy> cart){
            System.out.println("\nКорзина");
            if (cart.isEmpty()){
                System.out.println("Корзина пуста");
                return;
            }
            double sum =0;
            for (int i=0; i<cart.size();i++){
                System.out.println((i + 1) + ") " + cart.get(i).getName() + " - " + cart.get(i).getPrice() + "₸");
                cart.get(i).displayToyInfo();
                sum +=cart.get(i).getPrice();
            }
            System.out.println("Итого: " +sum +"₸");
        }
        private static void addServicesToItem(Scanner scanner, List<Toy> cart){
            if (cart.isEmpty()){
                System.out.println("Корзина пуста");
                return;
            }
            System.out.println("\nК какому товару добавить услуги?");
            for (int i =0; i <cart.size(); i++){
                System.out.println((i + 1) + ") " + cart.get(i).getName());
                cart.get(i).displayToyInfo();
            }
            System.out.println(">");
            int idx =Integer.parseInt(scanner.nextLine())-1;
            if (idx<0 || idx >= cart.size()){
                System.out.println("Такого товара нет");
                return;
            }
            Toy toy = cart.get(idx);
            boolean adding =true;

            while (adding){
                System.out.println("\nВыберите услугу:");
                System.out.println("1-Подарочная упаковка (+500)");
                System.out.println("2-Гарантия на год (+1000)");
                System.out.println("3-Быстрая доставка (+1500)");
                System.out.println("0-Готово");
                System.out.println(">");

                int ch =Integer.parseInt(scanner.nextLine());
                switch (ch){
                    case 1 ->toy =new Wrapping(toy);
                    case 2 ->toy =new Warranty(toy);
                    case 3 ->toy =new Delivery(toy);
                    case 0 ->adding =false;
                    default -> System.out.println("Нет такой опции");
                }
            }
            cart.set(idx,toy);
            System.out.println("Услуги добавлены");
        }
        private static void showDiscountInfo(){
            System.out.println("\nИнформация о скидках");
            System.out.println("Праздничные дни Казахстана: Праздничная скидка 20%");
            System.out.println("Суббота: Распродажа 50%");
            System.out.println("Остальные дни: Без скидки");
        }
        private static boolean checkout(Scanner scanner, OrderFacade orderFacade, List<Toy> cart, Discount discount){
            if (cart.isEmpty()){
                System.out.println("Корзина пуста");
                return false;
            }
            System.out.println("\n Оформление заказа");
            double total =0;
            for (Toy toy :cart) total +=toy.getPrice();
            System.out.println("Итого без скидки:" +total+"₸");
            double finalPrice =discount.discount(total);
            System.out.println("С учетом скидки (" +discount.name() + "):" +finalPrice +"₸");

            System.out.println("\nСпособ оплаты:");
            System.out.println("1-Kaspi");
            System.out.println("2-PayPal");
            System.out.println(">");

            int pay =Integer.parseInt(scanner.nextLine());
            PaymentSystem paymentSystem;
            String data;

            if (pay ==1){
                paymentSystem =new KaspiAdapter(new KaspiPayment());
                System.out.println("Телефон для Kaspi:");
                data =scanner.nextLine();
            } else if (pay ==2) {
                paymentSystem =new PayPalAdapter(new PayPalPayment());
                System.out.println("Email PayPal: ");
                data =scanner.nextLine();
            } else {
                System.out.println("Неверный способ");
                return false;
            }
            String result =orderFacade.checkout(cart, discount, paymentSystem, data);
            System.out.println(result);
            return true;
        }
        private static Discount chooseDiscountByDate(LocalDate date){
            int day =date.getDayOfMonth();
            int month =date.getMonthValue();;

            boolean isHoliday =
                    (day ==1 && month ==1) ||
                            (day == 2 && month == 1) ||
                            (day == 8 && month == 3) ||
                            (month == 3 && day >= 21 && day <= 23) ||
                            (day == 1 && month == 5) ||
                            (day == 7 && month == 5) ||
                            (day == 9 && month == 5) ||
                            (day == 6 && month == 7) ||
                            (day == 30 && month == 8) ||
                            (day == 25 && month == 10) ||
                            (day == 16 && month == 12);
            if (isHoliday) return new Holiday();
            if(date.getDayOfWeek() ==DayOfWeek.SATURDAY)
                return new Sale();
            return new Nodiscount();
    }
}