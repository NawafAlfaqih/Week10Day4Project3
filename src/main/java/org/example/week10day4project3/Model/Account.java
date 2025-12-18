package org.example.week10day4project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "account number cannot be empty")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$",
            message = "Account number must follow pattern -> 'XXXX-XXXX-XXXX-XXXX' with numbers only")
    @Column(columnDefinition = "varchar(20) not null")
    private String accountNumber;

    private Double balance;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActive;

    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
