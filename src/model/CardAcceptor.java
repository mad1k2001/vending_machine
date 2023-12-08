package model;

import util.MoneyAcceptor;

public class CardAcceptor implements MoneyAcceptor {
    private int balance;
    private String cardNumber;
    private String password;

    public CardAcceptor(int initialBalance, String cardNumber, String password) {
        this.balance = initialBalance;
        this.cardNumber = cardNumber;
        this.password = password;
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
        return "cards".equalsIgnoreCase(currencyType);
    }

    @Override
    public boolean authenticate(String cardNumber, String password) {
        return this.cardNumber.equals(cardNumber) && this.password.equals(password);
    }

    @Override
    public String getInfo() {
        return "Карточный приемник. Текущий баланс: " + balance;
    }
}
