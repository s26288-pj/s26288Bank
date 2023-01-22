package com.example.s26288bank.controler;

import com.example.s26288bank.exception.ValidationException;
import com.example.s26288bank.model.BankAccount;
import com.example.s26288bank.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
public class BankController {
    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/registerAccount")
    public ResponseEntity<BankAccount> registerAccount(@RequestBody BankAccount bankAccount) {
        try {
            bankService.addAccount(bankAccount);
        }
        catch (ValidationException validationException) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bankAccount);
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> allAccounts = bankService.findAllAccounts();

        return ResponseEntity.ok(allAccounts);
    }

    @GetMapping("/checkAccount")
    public ResponseEntity<Optional<BankAccount>> getAccountBalance(@RequestParam(required = true, name="id") int id) {
        Optional<BankAccount> bankAccount = bankService.findAccountById(id);
        return ResponseEntity.ok(bankAccount);
    }

    @PostMapping("/cashin")
    public ResponseEntity<ResponseEntity<Optional<BankAccount>>> cashIn(@RequestParam(required = true, name="ammount") int ammount, @RequestParam(required = true, name="account_id") int account_id) {
        Optional<BankAccount> bankAccount = bankService.findAccountById(account_id);
        try {
            bankService.addBalance(bankAccount, ammount);
        } catch (ValidationException e) {
            throw new ValidationException("Error");
        }
        return ResponseEntity.ok(getAccountBalance(bankAccount.get().getId()));
    }

    @PostMapping("/cashout")
    public ResponseEntity<ResponseEntity<Optional<BankAccount>>> cashOut(@RequestParam(required = true, name="ammount") int ammount, @RequestParam(required = true, name="account_id") int account_id) {
        Optional<BankAccount> bankAccount = bankService.findAccountById(account_id);
        try {
            bankService.removeBalance(bankAccount, ammount);
        } catch (ValidationException e) {
            throw new ValidationException("Not enough money!");
        }

        if (bankService.checkBalance(bankAccount) <= 0) {
            bankService.addBalance(bankAccount, ammount);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(getAccountBalance(bankAccount.get().getId()));
        }
    }
}
