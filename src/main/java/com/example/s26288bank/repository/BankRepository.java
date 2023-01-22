package com.example.s26288bank.repository;

import com.example.s26288bank.model.BankAccount;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BankRepository {
    private List<BankAccount> bankList = new ArrayList<>();

    public void addAccount(BankAccount account) throws Exception {
        if(bankList.contains(account)){
            throw new Exception();
        }
        bankList.add(account);
    }

    public Optional<BankAccount> findAccountById(int id) {
        return bankList.stream().filter(it -> it.getId() == id).findFirst();
    }

    public List<BankAccount> findAll() {
        return bankList;
    }

}
