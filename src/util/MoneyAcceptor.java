package util;

public interface MoneyAcceptor {

    void deposit(int amount);

    int getBalance();

    boolean hasSufficientFunds(int amount);

    void withdraw(int amount);

    boolean supports(String currencyType);

    default boolean authenticate(String cardNumber, String password) {
        return true;
    }

    String getInfo();
}
