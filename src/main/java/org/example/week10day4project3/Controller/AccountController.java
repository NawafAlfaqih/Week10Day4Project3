package org.example.week10day4project3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Model.Account;
import org.example.week10day4project3.Model.Customer;
import org.example.week10day4project3.Model.Employee;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.getAllAccounts(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@AuthenticationPrincipal Customer customer,
                                           @RequestBody @Valid Account account) {
        accountService.createAccount(customer.getId(), account);
        return ResponseEntity.status(200).body("Account Created Successfully");
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<?> updateAccount(@AuthenticationPrincipal Employee employee, @PathVariable Integer accountId,
                                           @RequestBody @Valid Account account) {
        accountService.updateAccount(employee.getId(), accountId, account);
        return ResponseEntity.status(200).body("Account updated Successfully");
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal Customer customer,
                                           @PathVariable Integer accountId) {
        accountService.deleteAccount(customer.getId(), accountId);
        return ResponseEntity.status(200).body("Account deleted Successfully");
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(@AuthenticationPrincipal Employee employee,
                                           @PathVariable Integer accountId) {
        accountService.activateAccount(employee.getId(), accountId);
        return ResponseEntity.status(200).body("Account activated Successfully");
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity<?> blockAccount(@AuthenticationPrincipal Employee employee,
                                             @PathVariable Integer accountId) {
        accountService.blockAccount(employee.getId(), accountId);
        return ResponseEntity.status(200).body("Account blocked Successfully");
    }

    @GetMapping("/get/details/{accountId}")
    public ResponseEntity<?> accountDetails(@AuthenticationPrincipal User user,
                                            @PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.accountDetails(user.getId(), accountId));
    }

    @GetMapping("/get/accounts")
    public ResponseEntity<?> getCustomerAccounts(@AuthenticationPrincipal Customer customer) {
        return ResponseEntity.status(200).body(accountService.getCustomerAccounts(customer.getId()));
    }

    @PutMapping("/deposit/account-id/{accountId}/amount/{amount}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal Customer customer,
                                     @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(customer.getId(), accountId, amount);
        return ResponseEntity.status(200).body("Deposit is done Successfully");
    }

    @PutMapping("/withdraw/account-id/{accountId}/amount/{amount}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal Customer customer,
                                     @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.withdraw(customer.getId(), accountId, amount);
        return ResponseEntity.status(200).body("withdraw is done Successfully");
    }

    @PutMapping("/transfer/account-id/{accountId}/target/{targetAccountId}/amount/{amount}")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal Customer customer,@PathVariable Integer accountId,
                                      @PathVariable Integer targetAccountId, @PathVariable Double amount) {
        accountService.transfer(customer.getId(), accountId,targetAccountId, amount);
        return ResponseEntity.status(200).body("transfer is done Successfully");
    }
}
