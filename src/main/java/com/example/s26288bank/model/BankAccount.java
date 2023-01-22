package com.example.s26288bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankAccount {
    private final int id;
    private final String name;
    private final String surname;
    private int balance;

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getBalance() {
        return balance;
    }
}
