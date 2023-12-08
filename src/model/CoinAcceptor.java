package model;

import util.MoneyAcceptor;

public class CoinAcceptor implements MoneyAcceptor {
    private int balance;

    public CoinAcceptor(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public void deposit(int amount) {
        balance += amount;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public boolean hasSufficientFunds(int amount) {
        return balance >= amount;
    }

    @Override
    public void withdraw(int amount) {
        if (hasSufficientFunds(amount)) {
            balance -= amount;
        } else {
            System.out.println("Недостаточно средств на счете.");
        }
    }

    @Override
    public boolean supports(String currencyType) {
        return "coins".equalsIgnoreCase(currencyType);
    }

    @Override
    public String getInfo() {
        return "Монетоприемник. Текущий баланс: " + balance;
    }
}
