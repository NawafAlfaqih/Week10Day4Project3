package org.example.week10day4project3.DTO_IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterCustomerDTO {

    @NotBlank(message = "Username Cannot be empty.")
    @Size(min = 4, max = 10, message = "username's length must be between 4 and 10 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password's length must be at least 6 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Password must be at least 6 characters with 1 uppercase," +
                    " 1 lowercase, 1 number, and 1 special character.")
    private String password;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 4, max = 20, message = "name's length must be between 4 and 20 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @NotBlank(message = "Phone Number cannot be blank")
    @Size(min = 10, max = 10, message = "Phone number must start with '05' and must be 10 digits in length")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and must be 10 digits in length")
    private String phoneNumber;
}
