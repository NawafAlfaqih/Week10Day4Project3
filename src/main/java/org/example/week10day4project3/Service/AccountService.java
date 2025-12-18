package org.example.week10day4project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Api.ApiException;
import org.example.week10day4project3.DTO_OUT.AccountDetailsDTO;
import org.example.week10day4project3.Model.Account;
import org.example.week10day4project3.Model.Customer;
import org.example.week10day4project3.Model.Employee;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Repository.AccountRepository;
import org.example.week10day4project3.Repository.CustomerRepository;
import org.example.week10day4project3.Repository.EmployeeRepository;
import org.example.week10day4project3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public List<Account> getAllAccounts(Integer adminId) {
        User user = userRepository.findUserById(adminId);
        if (user == null)
            throw new ApiException("user not found");

        if (!"ADMIN".equals(user.getRole()))
            throw new ApiException("UnAuthorized, Not Admin");

        return accountRepository.findAll();
    }

    public void createAccount(Integer customerId, Account account) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer not found");

        account.setCustomer(customer);
        account.setBalance(0.0);
        account.setIsActive(false);

        accountRepository.save(account);
    }

    public void updateAccount(Integer employeeId, Integer accountId, Account account) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null)
            throw new ApiException("Employee was not found");

        Account oldAccount = accountRepository.findAccountById(accountId);
        if (oldAccount == null)
            throw new ApiException("Account was not found");

        oldAccount.setAccountNumber(account.getAccountNumber());

        accountRepository.save(oldAccount);
    }

    public void deleteAccount(Integer customerId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer not found");

        Account oldAccount = accountRepository.findAccountById(accountId);
        if (oldAccount == null)
            throw new ApiException("Account was not found");

        if (!oldAccount.getCustomer().equals(customer))
            throw new ApiException("Customer is not owner of Account");

        accountRepository.delete(oldAccount);
    }

    public void activateAccount(Integer employeeId, Integer accountId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null)
            throw new ApiException("Employee was not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null)
            throw new ApiException("Account was not found");

        account.setIsActive(true);

        accountRepository.save(account);
    }

    public void blockAccount(Integer employeeId, Integer accountId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null)
            throw new ApiException("Employee was not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null)
            throw new ApiException("Account was not found");

        account.setIsActive(false);

        accountRepository.save(account);
    }

    public AccountDetailsDTO accountDetails(Integer userId, Integer accountId) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("user not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null)
            throw new ApiException("Account was not found");

        if (!account.getCustomer().getId().equals(userId)
                && !"ADMIN".equals(user.getRole()) && "EMPLOYEE".equals(user.getRole()))
            throw new ApiException("Not allowed to view details");

        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(
                account.getCustomer().getUser().getName(),
                account.getCustomer().getUser().getUsername(),
                account.getCustomer().getUser().getEmail(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getIsActive()
        );

        return accountDetailsDTO;
    }

    public Set<Account> getCustomerAccounts(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer was not found");

        return customer.getAccounts();
    }

    public void deposit(Integer customerId, Integer accountId, Double amount) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer was not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null)
            throw new ApiException("Account was not found");

        if (!account.getIsActive())
            throw new ApiException("Account is inactive");

        if (!account.getCustomer().getId().equals(customerId))
            throw new ApiException("Not allowed to deposit");

        if (amount < 0)
            throw new ApiException("can't deposit a negative amount");

        account.setBalance(account.getBalance() + amount);

        accountRepository.save(account);
    }

    public void withdraw(Integer customerId, Integer accountId, Double amount) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer was not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null)
            throw new ApiException("Account was not found");

        if (!account.getIsActive())
            throw new ApiException("Account is inactive");

        if (!account.getCustomer().getId().equals(customerId))
            throw new ApiException("Not allowed to withdraw");

        if (account.getBalance() < amount)
            throw new ApiException("Insufficient balance");

        account.setBalance(account.getBalance() - amount);

        accountRepository.save(account);
    }

    public void transfer(Integer customerId, Integer accountId,
                         Integer targetAccountId, Double amount) {

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer was not found");

        Account account = accountRepository.findAccountById(accountId);
        Account targetAccount = accountRepository.findAccountById(targetAccountId);
        if (account == null || targetAccount == null)
            throw new ApiException("Account was not found");

        if (!account.getIsActive())
            throw new ApiException("Account is inactive");

        if (!targetAccount.getIsActive())
            throw new ApiException("The Account you are trying to transfer to is inactive");

        if (!account.getCustomer().getId().equals(customerId))
            throw new ApiException("Not allowed to withdraw");

        if (Objects.equals(accountId, targetAccountId))
            throw new ApiException("Can't transfer on the same account");

        if (account.getBalance() < amount)
            throw new ApiException("Insufficient balance");

        account.setBalance(account.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        accountRepository.save(account);
        accountRepository.save(targetAccount);
    }


}
