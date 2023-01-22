package com.example.s26288bank.service;

import com.example.s26288bank.exception.DatabaseException;
import com.example.s26288bank.exception.ValidationException;
import com.example.s26288bank.model.BankAccount;
import com.example.s26288bank.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void addAccount(BankAccount bankAccount) {
        if(isValid(bankAccount.getName())) {
            throw new ValidationException("Name is required!");
        }

        if(isValid(bankAccount.getSurname())) {
            throw new ValidationException("Surname is required!");
        }

        if(isNotZero(bankAccount.getBalance())) {
            throw new ValidationException("Balance is required!");
        }

        try {
            bankRepository.addAccount(bankAccount);
        } catch (Exception e) {
            throw new DatabaseException("Database error: ", e);
        }
    }

    public int checkBalance(Optional<BankAccount> bankAccount) {
        return bankAccount.get().getBalance();
    }

    private static boolean isValid(String attribute) {
        return attribute == null || attribute.isBlank();
    }

    private static boolean isNotZero(int attribute) {
        if(attribute == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<BankAccount> findAllAccounts() {
        List<BankAccount> bankAccounts = bankRepository.findAll();

        return bankAccounts;
    }

    public Optional<BankAccount> findAccountById(int id) {
        return bankRepository.findAccountById(id);
    }

    public Optional<BankAccount> addBalance(Optional<BankAccount> bankAccount, int ammount) {
        bankAccount.get().setBalance(bankAccount.get().getBalance()+ammount);
        return bankAccount;
    }

    public Optional<BankAccount> removeBalance(Optional<BankAccount> bankAccount, int ammount) {
        bankAccount.get().setBalance(bankAccount.get().getBalance()-ammount);
        return bankAccount;
    }
}
