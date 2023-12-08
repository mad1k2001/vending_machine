import util.MoneyAcceptor;

public class Main {
    public static void main(String[] args) {
        MoneyAcceptor moneyAcceptor = AppRunner.chooseMoneyAcceptor();
        if (moneyAcceptor != null) {
            AppRunner app = new AppRunner(moneyAcceptor);
            app.run();
        }
    }
}
