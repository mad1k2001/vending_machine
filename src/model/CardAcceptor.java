package model;

import util.MoneyAcceptor;

public class CardAcceptor implements MoneyAcceptor {
    private int balance;

    public CardAcceptor(int initialBalance) {
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
            // Обработка ситуации, когда средств недостаточно
            System.out.println("Недостаточно средств на счете.");
        }
    }

    @Override
    public boolean supports(String currencyType) {
        return "cards".equalsIgnoreCase(currencyType);
    }

    @Override
    public String getInfo() {
        return "Карточный приемник. Текущий баланс: " + balance;
    }
}
