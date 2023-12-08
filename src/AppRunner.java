import enums.ActionLetter;
import model.*;
import util.MoneyAcceptor;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final MoneyAcceptor moneyAcceptor;

    private static boolean isExit = false;

    protected AppRunner(MoneyAcceptor moneyAcceptor) {
        this.moneyAcceptor = moneyAcceptor;
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }


    protected static MoneyAcceptor chooseMoneyAcceptor() {
        print("Выберите тип приемника (1 - Карточный, 2 - Монеты):");
        String choice = fromConsole();
        switch (choice) {
            case "1":
                return createCardAcceptor();
            case "2":
                return createCoinAcceptor();
            default:
                print("Недопустимый выбор. Попробуйте еще раз.");
                return chooseMoneyAcceptor();
        }
    }

    private static CardAcceptor createCardAcceptor() {
        print("Введите баланс для карточного приемника:");
        int balance = Integer.parseInt(fromConsole());
        print("Введите номер карты:");
        String cardNumber = fromConsole();
        print("Введите пароль:");
        String password = fromConsole();
        return new CardAcceptor(balance, cardNumber, password);
    }

    private static CoinAcceptor createCoinAcceptor() {
        print("Введите начальный баланс для монетоприемника:");
        int balance = Integer.parseInt(fromConsole());
        return new CoinAcceptor(balance);
    }

    public void run() {
        while (!isExit) {
            startSimulation();
        }
    }

    private void startSimulation() {

        print("В автомате доступны:");
        showProducts(products);

        print("Средства на счету: " + moneyAcceptor.getBalance());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }


    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyAcceptor.hasSufficientFunds(products.get(i).getPrice())) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Внести средства");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            deposit();
            return;
        }
        if ("h".equalsIgnoreCase(action)) {
            isExit = true;}
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    purchase(products.get(i));
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                print("Exception: " + e.getMessage());
                chooseAction(products);
            }
        }
    }

    private void deposit() {
        try {
            print("Введите сумму для внесения:");
            int amount = Integer.parseInt(fromConsole());
            moneyAcceptor.deposit(amount);
            print("Вы внесли " + amount + " средств.");
            print("Новый баланс: " + moneyAcceptor.getBalance());
        } catch (NumberFormatException e) {
            print("Ошибка ввода. Введите корректное число.");
        }
    }

    private void purchase(Product product) {
        if (moneyAcceptor.hasSufficientFunds(product.getPrice())) {
            moneyAcceptor.withdraw(product.getPrice());
            print("Вы купили " + product.getName());
        } else {
            print("Недостаточно средств. Внесите дополнительные средства.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private static String fromConsole() {
        return new Scanner(System.in).nextLine().trim();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }


    private static void print(String msg) {
        System.out.println(msg);
    }
}
