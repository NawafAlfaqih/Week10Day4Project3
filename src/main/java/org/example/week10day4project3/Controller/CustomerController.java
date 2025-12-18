package org.example.week10day4project3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.DTO_IN.RegisterCustomerDTO;
import org.example.week10day4project3.Model.Customer;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCustomers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getAllCustomers(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCustomer(@RequestBody @Valid RegisterCustomerDTO customerDTO) {
        customerService.addCustomer(customerDTO);
        return ResponseEntity.status(200).body("Customer Added Successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal Customer customer,
                                            @RequestBody @Valid RegisterCustomerDTO customerDTO) {
        customerService.updateCustomer(customer.getId(), customerDTO);
        return ResponseEntity.status(200).body("Customer Updated Successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@AuthenticationPrincipal Customer customer) {
        customerService.deleteCustomer(customer.getId());
        return ResponseEntity.status(200).body("Customer deleted Successfully");
    }

}
