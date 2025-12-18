package org.example.week10day4project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Api.ApiException;
import org.example.week10day4project3.DTO_IN.RegisterEmployeeDTO;
import org.example.week10day4project3.Model.Employee;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Repository.EmployeeRepository;
import org.example.week10day4project3.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public List<Employee> getAllEmployees(Integer adminId) {
        User user = userRepository.findUserById(adminId);
        if (user == null)
            throw new ApiException("user not found");

        if (!"ADMIN".equals(user.getRole()))
            throw new ApiException("UnAuthorized, Not Admin");

        return employeeRepository.findAll();
    }

    public void addEmployee(Integer adminId, RegisterEmployeeDTO employeeDTO) {

        User admin = userRepository.findUserById(adminId);
        if (admin == null)
            throw new ApiException("user not found");

        if (!"ADMIN".equals(admin.getRole()))
            throw new ApiException("UnAuthorized, Not Admin");

        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());

        User user = new User(
                null,
                employeeDTO.getUsername(),
                hash,
                employeeDTO.getName(),
                employeeDTO.getEmail(),
                "EMPLOYEE",
                null,
                null
        );

        Employee employee = new Employee(
                null, employeeDTO.getPosition(), employeeDTO.getSalary(), user
        );

        user.setEmployee(employee);
        userRepository.save(user);
    }

    public void updateEmployee(Integer adminId, Integer employeeId, RegisterEmployeeDTO employeeDTO) {
        User admin = userRepository.findUserById(adminId);
        if (admin == null)
            throw new ApiException("user not found");

        if (!"ADMIN".equals(admin.getRole()))
            throw new ApiException("UnAuthorized, Not Admin");

        Employee oldEmployee = employeeRepository.findEmployeeById(employeeId);
        if (oldEmployee == null)
            throw new ApiException("Employee was not found");

        oldEmployee.getUser().setUsername(employeeDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        oldEmployee.getUser().setPassword(hash);
        oldEmployee.getUser().setName(employeeDTO.getName());
        oldEmployee.setPosition(employeeDTO.getPosition());
        oldEmployee.setSalary(employeeDTO.getSalary());

        employeeRepository.save(oldEmployee);
    }

    public void deleteEmployee(Integer adminId, Integer employeeId){
        Employee oldEmployee = employeeRepository.findEmployeeById(employeeId);
        if (oldEmployee == null)
            throw new ApiException("Employee was not found");

        employeeRepository.delete(oldEmployee);
    }
}
