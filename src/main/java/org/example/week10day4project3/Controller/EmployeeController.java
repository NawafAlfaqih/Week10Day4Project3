package org.example.week10day4project3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.DTO_IN.RegisterEmployeeDTO;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllEmployees(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@AuthenticationPrincipal User user,
                                         @RequestBody @Valid RegisterEmployeeDTO employeeDTO) {
        employeeService.addEmployee(user.getId(), employeeDTO);
        return ResponseEntity.status(200).body("Employee Added Successfully");
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@AuthenticationPrincipal User user, @PathVariable Integer employeeId,
                                            @RequestBody @Valid RegisterEmployeeDTO employeeDTO) {
        employeeService.updateEmployee(user.getId(),employeeId, employeeDTO);
        return ResponseEntity.status(200).body("Employee Updated Successfully");
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@AuthenticationPrincipal User user, @PathVariable Integer employeeId) {
        employeeService.deleteEmployee(user.getId(), employeeId);
        return ResponseEntity.status(200).body("Employee deleted Successfully");
    }
}
