package org.example.week10day4project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Api.ApiException;
import org.example.week10day4project3.DTO_IN.RegisterCustomerDTO;
import org.example.week10day4project3.Model.Account;
import org.example.week10day4project3.Model.Customer;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Repository.CustomerRepository;
import org.example.week10day4project3.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public List<Customer> getAllCustomers(Integer adminId) {
        User user = userRepository.findUserById(adminId);
        if (user == null)
            throw new ApiException("user not found");

        if (!"ADMIN".equals(user.getRole()))
            throw new ApiException("UnAuthorized, Not Admin");

        return customerRepository.findAll();
    }

    public void addCustomer(RegisterCustomerDTO customerDTO) {

        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());

        User user = new User(
                null,
                customerDTO.getUsername(),
                hash,
                customerDTO.getName(),
                customerDTO.getEmail(),
                "CUSTOMER",
                null,
                null
        );

        Customer customer = new Customer(
            null, customerDTO.getPhoneNumber(), user, null
        );

        user.setCustomer(customer);
        userRepository.save(user);
    }

    public void updateCustomer(Integer customerId, RegisterCustomerDTO customerDTO) {
        Customer oldCustomer = customerRepository.findCustomerById(customerId);
        if (oldCustomer == null)
            throw new ApiException("Customer was not found");



        oldCustomer.getUser().setUsername(customerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        oldCustomer.getUser().setPassword(hash);
        oldCustomer.getUser().setName(customerDTO.getName());
        oldCustomer.setPhoneNumber(customerDTO.getPhoneNumber());

        customerRepository.save(oldCustomer);
    }

    public void deleteCustomer(Integer customerId) {
        Customer oldCustomer = customerRepository.findCustomerById(customerId);
        if (oldCustomer == null)
            throw new ApiException("Customer was not found");

        customerRepository.delete(oldCustomer);
    }
}
