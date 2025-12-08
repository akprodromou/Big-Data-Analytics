package oss1.readwrite;

public class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public synchronized void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Can't deposit.");
        }
        this.balance += amount;
        System.out.println("Deposit " + amount + " in thread " + Thread.currentThread().getId() + ", balance is " + balance);
    }

    public synchronized void withdraw(double amount) {
        if (amount < 0 || amount > this.balance) {
            throw new IllegalArgumentException("Can't withdraw.");
        }
        this.balance -= amount;
        System.out.println("Withdraw " + amount + " in thread " + Thread.currentThread().getId() + ", balance is " + balance);
    }
}